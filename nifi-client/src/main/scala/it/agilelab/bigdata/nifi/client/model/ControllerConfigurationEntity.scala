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
package it.agilelab.bigdata.nifi.client.model

import it.agilelab.bigdata.nifi.client.core.ApiModel

case class ControllerConfigurationEntity(
  revision: Option[RevisionDTO] = None,
  permissions: Option[PermissionsDTO] = None,
  /* Acknowledges that this node is disconnected to allow for mutable requests to proceed. */
  disconnectedNodeAcknowledged: Option[Boolean] = None,
  component: Option[ControllerConfigurationDTO] = None
) extends ApiModel


