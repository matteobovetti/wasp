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

case class VersionedPort(
  /* The component's unique identifier */
  identifier: Option[String] = None,
  /* The component's name */
  name: Option[String] = None,
  /* The user-supplied comments for the component */
  comments: Option[String] = None,
  position: Option[Position] = None,
  /* The type of port. */
  `type`: Option[VersionedPortEnums.`Type`] = None,
  /* The number of tasks that should be concurrently scheduled for the port. */
  concurrentlySchedulableTaskCount: Option[Int] = None,
  /* The scheduled state of the component */
  scheduledState: Option[VersionedPortEnums.ScheduledState] = None,
  /* Whether or not this port allows remote access for site-to-site */
  allowRemoteAccess: Option[Boolean] = None,
  componentType: Option[VersionedPortEnums.ComponentType] = None,
  /* The ID of the Process Group that this component belongs to */
  groupIdentifier: Option[String] = None
) extends ApiModel

object VersionedPortEnums {

  type `Type` = `Type`.Value
  type ScheduledState = ScheduledState.Value
  type ComponentType = ComponentType.Value
  object `Type` extends Enumeration {
    val INPUTPORT = Value("INPUT_PORT")
    val OUTPUTPORT = Value("OUTPUT_PORT")
  }

  object ScheduledState extends Enumeration {
    val ENABLED = Value("ENABLED")
    val DISABLED = Value("DISABLED")
  }

  object ComponentType extends Enumeration {
    val CONNECTION = Value("CONNECTION")
    val PROCESSOR = Value("PROCESSOR")
    val PROCESSGROUP = Value("PROCESS_GROUP")
    val REMOTEPROCESSGROUP = Value("REMOTE_PROCESS_GROUP")
    val INPUTPORT = Value("INPUT_PORT")
    val OUTPUTPORT = Value("OUTPUT_PORT")
    val REMOTEINPUTPORT = Value("REMOTE_INPUT_PORT")
    val REMOTEOUTPUTPORT = Value("REMOTE_OUTPUT_PORT")
    val FUNNEL = Value("FUNNEL")
    val LABEL = Value("LABEL")
    val CONTROLLERSERVICE = Value("CONTROLLER_SERVICE")
  }

}

