package it.agilelab.bigdata.wasp.consumers.spark.plugins.solr

import it.agilelab.bigdata.wasp.core.WaspMessage
import it.agilelab.bigdata.wasp.models.configuration.SolrConfigModel
import org.apache.solr.client.solrj.SolrQuery

trait SolrAdminMessage extends WaspMessage

case class Search(collection: String = SolrAdminActor.collection, query: Option[Map[String, String]], sort: Option[Map[String, SolrQuery.ORDER]], from: Int, size: Int) extends SolrAdminMessage

case class AddCollection(collection: String = SolrAdminActor.collection, numShards: Int = SolrAdminActor.numShards, replicationFactor: Int = SolrAdminActor.replicationFactor) extends SolrAdminMessage

case class AddMapping(collection: String = SolrAdminActor.collection, schema: String = SolrAdminActor.schema, numShards: Int = SolrAdminActor.numShards, replicationFactor: Int = SolrAdminActor.replicationFactor) extends SolrAdminMessage

case class AddAlias(collection: String = SolrAdminActor.collection, alias: String = SolrAdminActor.alias) extends SolrAdminMessage

case class RemoveCollection(collection: String = SolrAdminActor.collection) extends SolrAdminMessage

case class RemoveAlias(collection: String = SolrAdminActor.collection, alias: String = SolrAdminActor.alias) extends SolrAdminMessage

case class Initialization(solrConfigModel: SolrConfigModel) extends SolrAdminMessage

case class CheckCollection(collection: String = SolrAdminActor.collection) extends SolrAdminMessage

case class CheckOrCreateCollection(collection: String = SolrAdminActor.collection, schema: String = SolrAdminActor.schema, numShards: Int = 1, replicationFactor: Int = 1) extends SolrAdminMessage