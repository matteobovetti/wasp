/**
 * NiFi Rest Api
 * The Rest Api provides programmatic access to command and control a NiFi instance in real time. Start and                                              stop processors, monitor queues, query provenance data, and more. Each endpoint below includes a description,                                             definitions of the expected input and output, potential response codes, and the authorizations required                                             to invoke each service.
 *
 * The version of the OpenAPI document: 1.11.4
 * Contact: dev@nifi.apache.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package it.agilelab.bigdata.nifi.client.api

import it.agilelab.bigdata.nifi.client.core.SttpSerializer
import it.agilelab.bigdata.nifi.client.core.alias._
import it.agilelab.bigdata.nifi.client.model.{PortEntity, PortRunStatusEntity, ProcessorEntity}
import sttp.client._
import sttp.model.Method

object InputPortsApi {

  def apply(baseUrl: String = "http://localhost/nifi-api")(implicit serializer: SttpSerializer) = new InputPortsApi(baseUrl)
}

class InputPortsApi(baseUrl: String)(implicit serializer: SttpSerializer) {

  import it.agilelab.bigdata.nifi.client.core.Helpers._
  import serializer._

  /**
   * Expected answers:
   *   code 200 : PortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The input port id.
   */
  def getInputPort(id: String): ApiRequestT[PortEntity] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/input-ports/${id}")
      .contentType("application/json")
      .response(asJson[PortEntity])

  /**
   * Expected answers:
   *   code 200 : PortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The input port id.
   * @param version The revision is used to verify the client is working with the latest version of the flow.
   * @param clientId If the client id is not specified, new one will be generated. This value (whether specified or generated) is included in the response.
   * @param disconnectedNodeAcknowledged Acknowledges that this node is disconnected to allow for mutable requests to proceed.
   */
  def removeInputPort(id: String, version: Option[String] = None, clientId: Option[String] = None, disconnectedNodeAcknowledged: Option[Boolean] = None): ApiRequestT[PortEntity] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/input-ports/${id}?version=$version&clientId=$clientId&disconnectedNodeAcknowledged=$disconnectedNodeAcknowledged")
      .contentType("application/json")
      .response(asJson[PortEntity])

  /**
   * Expected answers:
   *   code 200 : PortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The input port id.
   * @param body The input port configuration details.
   */
  def updateInputPort(id: String, body: PortEntity): ApiRequestT[PortEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/input-ports/${id}")
      .contentType("application/json")
      .body(body)
      .response(asJson[PortEntity])

  /**
   * Expected answers:
   *   code 200 : ProcessorEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The port id.
   * @param body The port run status.
   */
  def updateRunStatus(id: String, body: PortRunStatusEntity): ApiRequestT[ProcessorEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/input-ports/${id}/run-status")
      .contentType("application/json")
      .body(body)
      .response(asJson[ProcessorEntity])

}

