package com.briskware.taxi.actor

import akka.actor.{Actor, ActorLogging}
import com.briskware.taxi.model.Location

case class CloseToTubeStation(loc: Location)
case class CloseToTubeStationResponse(isClose: Boolean)

/**
 * This service implementation assumes that Tube stations are located within
 * the configured distance from the centre located at long/late 50.0/0.0
 */
class TubeLocationService extends Actor with ActorLogging {

  /**
   * distance is 1 minute lat/long
   */
  val distance = 1D/60D

  override def receive: Receive = {
    case CloseToTubeStation(loc) => sender ! CloseToTubeStationResponse(isCloseToTubeStation(loc))
  }

  private def isCloseToTubeStation(loc: Location) = loc match {
    case Location(long,lat) if Math.abs(Math.abs(lat) - 50.0D) < distance && Math.abs(long) < distance => true
    case _ => false
  }

}
