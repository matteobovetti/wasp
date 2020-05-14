/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.spark

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.classification.InterfaceAudience
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.spark.api.java.function.{FlatMapFunction, Function, VoidFunction}
import org.apache.spark.api.java.{JavaRDD, JavaSparkContext}
import org.apache.spark.streaming.api.java.JavaDStream

import scala.collection.JavaConversions._
import scala.reflect.ClassTag

/**
 * This is the Java Wrapper over HBaseContext which is written in
 * Scala.  This class will be used by developers that want to
 * work with Spark or Spark Streaming in Java
 *
 * @param jsc    This is the JavaSparkContext that we will wrap
 * @param config This is the config information to out HBase cluster
 */
@InterfaceAudience.Public
class JavaHBaseContext(@transient jsc: JavaSparkContext,
                       @transient config: Configuration) extends Serializable {
  val hbaseContext = new HBaseContext(jsc.sc, config)

  /**
   * A simple enrichment of the traditional Spark javaRdd foreachPartition.
   * This function differs from the original in that it offers the
   * developer access to a already connected Connection object
   *
   * Note: Do not close the Connection object.  All Connection
   * management is handled outside this method
   *
   * @param javaRdd Original javaRdd with data to iterate over
   * @param f       Function to be given a iterator to iterate through
   *                the RDD values and a Connection object to interact
   *                with HBase
   */
  def foreachPartition[T](javaRdd: JavaRDD[T],
                          f: VoidFunction[(java.util.Iterator[T], Connection)]) = {

    hbaseContext.foreachPartition(javaRdd.rdd,
      (it: Iterator[T], conn: Connection) => {
        f.call((it, conn))
      })
  }

  /**
   * A simple enrichment of the traditional Spark Streaming dStream foreach
   * This function differs from the original in that it offers the
   * developer access to a already connected Connection object
   *
   * Note: Do not close the Connection object.  All Connection
   * management is handled outside this method
   *
   * @param javaDstream Original DStream with data to iterate over
   * @param f           Function to be given a iterator to iterate through
   *                    the JavaDStream values and a Connection object to
   *                    interact with HBase
   */
  def foreachPartition[T](javaDstream: JavaDStream[T],
                          f: VoidFunction[(Iterator[T], Connection)]) = {
    hbaseContext.foreachPartition(javaDstream.dstream,
      (it: Iterator[T], conn: Connection) => f.call(it, conn))
  }

  /**
   * A simple enrichment of the traditional Spark JavaRDD mapPartition.
   * This function differs from the original in that it offers the
   * developer access to a already connected Connection object
   *
   * Note: Do not close the Connection object.  All Connection
   * management is handled outside this method
   *
   * Note: Make sure to partition correctly to avoid memory issue when
   * getting data from HBase
   *
   * @param javaRdd Original JavaRdd with data to iterate over
   * @param f       Function to be given a iterator to iterate through
   *                the RDD values and a Connection object to interact
   *                with HBase
   * @return        Returns a new RDD generated by the user definition
   *                function just like normal mapPartition
   */
  def mapPartitions[T, R](javaRdd: JavaRDD[T],
                          f: FlatMapFunction[(java.util.Iterator[T],
                            Connection), R]): JavaRDD[R] = {

    def fn = (it: Iterator[T], conn: Connection) =>
      asScalaIterator(
        f.call((asJavaIterator(it), conn))
      )

    JavaRDD.fromRDD(hbaseContext.mapPartitions(javaRdd.rdd,
      (iterator: Iterator[T], connection: Connection) =>
        fn(iterator, connection))(fakeClassTag[R]))(fakeClassTag[R])
  }

  /**
   * A simple enrichment of the traditional Spark Streaming JavaDStream
   * mapPartition.
   *
   * This function differs from the original in that it offers the
   * developer access to a already connected Connection object
   *
   * Note: Do not close the Connection object.  All Connection
   * management is handled outside this method
   *
   * Note: Make sure to partition correctly to avoid memory issue when
   * getting data from HBase
   *
   * @param javaDstream Original JavaDStream with data to iterate over
   * @param mp          Function to be given a iterator to iterate through
   *                    the JavaDStream values and a Connection object to
   *                    interact with HBase
   * @return            Returns a new JavaDStream generated by the user
   *                    definition function just like normal mapPartition
   */
  def streamMap[T, U](javaDstream: JavaDStream[T],
                      mp: Function[(Iterator[T], Connection), Iterator[U]]):
  JavaDStream[U] = {
    JavaDStream.fromDStream(hbaseContext.streamMapPartitions(javaDstream.dstream,
      (it: Iterator[T], conn: Connection) =>
        mp.call(it, conn))(fakeClassTag[U]))(fakeClassTag[U])
  }

  /**
   * A simple abstraction over the HBaseContext.foreachPartition method.
   *
   * It allow addition support for a user to take JavaRDD
   * and generate puts and send them to HBase.
   * The complexity of managing the Connection is
   * removed from the developer
   *
   * @param javaRdd   Original JavaRDD with data to iterate over
   * @param tableName The name of the table to put into
   * @param f         Function to convert a value in the JavaRDD
   *                  to a HBase Put
   */
  def bulkPut[T](javaRdd: JavaRDD[T],
                 tableName: TableName,
                 f: Function[(T), Put]) {

    hbaseContext.bulkPut(javaRdd.rdd, tableName, (t: T) => f.call(t))
  }

  /**
   * A simple abstraction over the HBaseContext.streamMapPartition method.
   *
   * It allow addition support for a user to take a JavaDStream and
   * generate puts and send them to HBase.
   *
   * The complexity of managing the Connection is
   * removed from the developer
   *
   * @param javaDstream Original DStream with data to iterate over
   * @param tableName   The name of the table to put into
   * @param f           Function to convert a value in
   *                    the JavaDStream to a HBase Put
   */
  def streamBulkPut[T](javaDstream: JavaDStream[T],
                       tableName: TableName,
                       f: Function[T, Put]) = {
    hbaseContext.streamBulkPut(javaDstream.dstream,
      tableName,
      (t: T) => f.call(t))
  }

  /**
   * A simple abstraction over the HBaseContext.foreachPartition method.
   *
   * It allow addition support for a user to take a JavaRDD and
   * generate delete and send them to HBase.
   *
   * The complexity of managing the Connection is
   * removed from the developer
   *
   * @param javaRdd   Original JavaRDD with data to iterate over
   * @param tableName The name of the table to delete from
   * @param f         Function to convert a value in the JavaRDD to a
   *                  HBase Deletes
   * @param batchSize The number of deletes to batch before sending to HBase
   */
  def bulkDelete[T](javaRdd: JavaRDD[T], tableName: TableName,
                    f: Function[T, Delete], batchSize: Integer) {
    hbaseContext.bulkDelete(javaRdd.rdd, tableName, (t: T) => f.call(t), batchSize)
  }

  /**
   * A simple abstraction over the HBaseContext.streamBulkMutation method.
   *
   * It allow addition support for a user to take a JavaDStream and
   * generate Delete and send them to HBase.
   *
   * The complexity of managing the Connection is
   * removed from the developer
   *
   * @param javaDStream Original DStream with data to iterate over
   * @param tableName   The name of the table to delete from
   * @param f           Function to convert a value in the JavaDStream to a
   *                    HBase Delete
   * @param batchSize   The number of deletes to be sent at once
   */
  def streamBulkDelete[T](javaDStream: JavaDStream[T],
                          tableName: TableName,
                          f: Function[T, Delete],
                          batchSize: Integer) = {
    hbaseContext.streamBulkDelete(javaDStream.dstream, tableName,
      (t: T) => f.call(t),
      batchSize)
  }

  /**
   * A simple abstraction over the HBaseContext.mapPartition method.
   *
   * It allow addition support for a user to take a JavaRDD and generates a
   * new RDD based on Gets and the results they bring back from HBase
   *
   * @param tableName     The name of the table to get from
   * @param batchSize     batch size of how many gets to retrieve in a single fetch
   * @param javaRdd       Original JavaRDD with data to iterate over
   * @param makeGet       Function to convert a value in the JavaRDD to a
   *                      HBase Get
   * @param convertResult This will convert the HBase Result object to
   *                      what ever the user wants to put in the resulting
   *                      JavaRDD
   * @return              New JavaRDD that is created by the Get to HBase
   */
  def bulkGet[T, U](tableName: TableName,
                    batchSize: Integer,
                    javaRdd: JavaRDD[T],
                    makeGet: Function[T, Get],
                    convertResult: Function[Result, U]): JavaRDD[U] = {

    JavaRDD.fromRDD(hbaseContext.bulkGet[T, U](tableName,
      batchSize,
      javaRdd.rdd,
      (t: T) => makeGet.call(t),
      (r: Result) => {
        convertResult.call(r)
      })(fakeClassTag[U]))(fakeClassTag[U])

  }

  /**
   * A simple abstraction over the HBaseContext.streamMap method.
   *
   * It allow addition support for a user to take a DStream and
   * generates a new DStream based on Gets and the results
   * they bring back from HBase
   *
   * @param tableName     The name of the table to get from
   * @param batchSize     The number of gets to be batched together
   * @param javaDStream   Original DStream with data to iterate over
   * @param makeGet       Function to convert a value in the JavaDStream to a
   *                      HBase Get
   * @param convertResult This will convert the HBase Result object to
   *                      what ever the user wants to put in the resulting
   *                      JavaDStream
   * @return              New JavaDStream that is created by the Get to HBase
   */
  def streamBulkGet[T, U](tableName: TableName,
                          batchSize: Integer,
                          javaDStream: JavaDStream[T],
                          makeGet: Function[T, Get],
                          convertResult: Function[Result, U]): JavaDStream[U] = {
    JavaDStream.fromDStream(hbaseContext.streamBulkGet(tableName,
      batchSize,
      javaDStream.dstream,
      (t: T) => makeGet.call(t),
      (r: Result) => convertResult.call(r))(fakeClassTag[U]))(fakeClassTag[U])
  }

  /**
   * This function will use the native HBase TableInputFormat with the
   * given scan object to generate a new JavaRDD
   *
   * @param tableName The name of the table to scan
   * @param scans     The HBase scan object to use to read data from HBase
   * @param f         Function to convert a Result object from HBase into
   *                  What the user wants in the final generated JavaRDD
   * @return          New JavaRDD with results from scan
   */
  def hbaseRDD[U](tableName: TableName,
                  scans: Scan,
                  f: Function[(ImmutableBytesWritable, Result), U]):
  JavaRDD[U] = {
    JavaRDD.fromRDD(
      hbaseContext.hbaseRDD[U](tableName,
        scans,
        (v: (ImmutableBytesWritable, Result)) =>
          f.call(v._1, v._2))(fakeClassTag[U]))(fakeClassTag[U])
  }

  /**
   * A overloaded version of HBaseContext hbaseRDD that define the
   * type of the resulting JavaRDD
   *
   * @param tableName The name of the table to scan
   * @param scans     The HBase scan object to use to read data from HBase
   * @return          New JavaRDD with results from scan
   */
  def hbaseRDD(tableName: TableName,
               scans: Scan):
  JavaRDD[(ImmutableBytesWritable, Result)] = {
    JavaRDD.fromRDD(hbaseContext.hbaseRDD(tableName, scans))
  }

  /**
   * Produces a ClassTag[T], which is actually just a casted ClassTag[AnyRef].
   *
   * This method is used to keep ClassTags out of the external Java API, as the Java compiler
   * cannot produce them automatically. While this ClassTag-faking does please the compiler,
   * it can cause problems at runtime if the Scala API relies on ClassTags for correctness.
   *
   * Often, though, a ClassTag[AnyRef] will not lead to incorrect behavior,
   * just worse performance or security issues.
   * For instance, an Array[AnyRef] can hold any type T,
   * but may lose primitive
   * specialization.
   */
  private[spark]
  def fakeClassTag[T]: ClassTag[T] = ClassTag.AnyRef.asInstanceOf[ClassTag[T]]

}
