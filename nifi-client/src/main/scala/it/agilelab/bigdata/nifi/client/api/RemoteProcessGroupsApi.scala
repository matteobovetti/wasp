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
import it.agilelab.bigdata.nifi.client.model.{ComponentStateEntity, RemotePortRunStatusEntity, RemoteProcessGroupEntity, RemoteProcessGroupPortEntity}
import sttp.client._
import sttp.model.Method

object RemoteProcessGroupsApi {

  def apply(baseUrl: String = "http://localhost/nifi-api")(implicit serializer: SttpSerializer) = new RemoteProcessGroupsApi(baseUrl)
}

class RemoteProcessGroupsApi(baseUrl: String)(implicit serializer: SttpSerializer) {

  import it.agilelab.bigdata.nifi.client.core.Helpers._
  import serializer._

  /**
   * Expected answers:
   *   code 200 : RemoteProcessGroupEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   */
  def getRemoteProcessGroup(id: String): ApiRequestT[RemoteProcessGroupEntity] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/remote-process-groups/${id}")
      .contentType("application/json")
      .response(asJson[RemoteProcessGroupEntity])

  /**
   * Expected answers:
   *   code 200 : ComponentStateEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The processor id.
   */
  def getState(id: String): ApiRequestT[ComponentStateEntity] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/remote-process-groups/${id}/state")
      .contentType("application/json")
      .response(asJson[ComponentStateEntity])

  /**
   * Expected answers:
   *   code 200 : RemoteProcessGroupEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param version The revision is used to verify the client is working with the latest version of the flow.
   * @param clientId If the client id is not specified, new one will be generated. This value (whether specified or generated) is included in the response.
   * @param disconnectedNodeAcknowledged Acknowledges that this node is disconnected to allow for mutable requests to proceed.
   */
  def removeRemoteProcessGroup(id: String, version: Option[String] = None, clientId: Option[String] = None, disconnectedNodeAcknowledged: Option[Boolean] = None): ApiRequestT[RemoteProcessGroupEntity] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/remote-process-groups/${id}?version=$version&clientId=$clientId&disconnectedNodeAcknowledged=$disconnectedNodeAcknowledged")
      .contentType("application/json")
      .response(asJson[RemoteProcessGroupEntity])

  /**
   * Expected answers:
   *   code 200 : RemoteProcessGroupEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param body The remote process group.
   */
  def updateRemoteProcessGroup(id: String, body: RemoteProcessGroupEntity): ApiRequestT[RemoteProcessGroupEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/remote-process-groups/${id}")
      .contentType("application/json")
      .body(body)
      .response(asJson[RemoteProcessGroupEntity])

  /**
   * Note: This endpoint is subject to change as NiFi and it's REST API evolve.
   * 
   * Expected answers:
   *   code 200 : RemoteProcessGroupPortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param portId The remote process group port id.
   * @param body The remote process group port.
   */
  def updateRemoteProcessGroupInputPort(id: String, portId: String, body: RemoteProcessGroupPortEntity): ApiRequestT[RemoteProcessGroupPortEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/remote-process-groups/${id}/input-ports/${portId}")
      .contentType("application/json")
      .body(body)
      .response(asJson[RemoteProcessGroupPortEntity])

  /**
   * Note: This endpoint is subject to change as NiFi and it's REST API evolve.
   * 
   * Expected answers:
   *   code 200 : RemoteProcessGroupPortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param portId The remote process group port id.
   * @param body The remote process group port.
   */
  def updateRemoteProcessGroupInputPortRunStatus(id: String, portId: String, body: RemotePortRunStatusEntity): ApiRequestT[RemoteProcessGroupPortEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/remote-process-groups/${id}/input-ports/${portId}/run-status")
      .contentType("application/json")
      .body(body)
      .response(asJson[RemoteProcessGroupPortEntity])

  /**
   * Note: This endpoint is subject to change as NiFi and it's REST API evolve.
   * 
   * Expected answers:
   *   code 200 : RemoteProcessGroupPortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param portId The remote process group port id.
   * @param body The remote process group port.
   */
  def updateRemoteProcessGroupOutputPort(id: String, portId: String, body: RemoteProcessGroupPortEntity): ApiRequestT[RemoteProcessGroupPortEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/remote-process-groups/${id}/output-ports/${portId}")
      .contentType("application/json")
      .body(body)
      .response(asJson[RemoteProcessGroupPortEntity])

  /**
   * Note: This endpoint is subject to change as NiFi and it's REST API evolve.
   * 
   * Expected answers:
   *   code 200 : RemoteProcessGroupPortEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param portId The remote process group port id.
   * @param body The remote process group port.
   */
  def updateRemoteProcessGroupOutputPortRunStatus(id: String, portId: String, body: RemotePortRunStatusEntity): ApiRequestT[RemoteProcessGroupPortEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/remote-process-groups/${id}/output-ports/${portId}/run-status")
      .contentType("application/json")
      .body(body)
      .response(asJson[RemoteProcessGroupPortEntity])

  /**
   * Expected answers:
   *   code 200 : RemoteProcessGroupEntity (successful operation)
   *   code 400 :  (NiFi was unable to complete the request because it was invalid. The request should not be retried without modification.)
   *   code 401 :  (Client could not be authenticated.)
   *   code 403 :  (Client is not authorized to make this request.)
   *   code 404 :  (The specified resource could not be found.)
   *   code 409 :  (The request was valid but NiFi was not in the appropriate state to process it. Retrying the same request later may be successful.)
   * 
   * @param id The remote process group id.
   * @param body The remote process group run status.
   */
  def updateRemoteProcessGroupRunStatus(id: String, body: RemotePortRunStatusEntity): ApiRequestT[RemoteProcessGroupEntity] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/remote-process-groups/${id}/run-status")
      .contentType("application/json")
      .body(body)
      .response(asJson[RemoteProcessGroupEntity])

}

