/*
 * ************************************************************************
 *  * ULLINK CONFIDENTIAL INFORMATION
 *  * _______________________________
 *  *
 *  * All Rights Reserved.
 *  *
 *  * NOTICE: This file and its content are the property of Ullink. The
 *  * information included has been classified as Confidential and may
 *  * not be copied, modified, distributed, or otherwise disseminated, in
 *  * whole or part, without the express written permission of Ullink.
 *  ***********************************************************************
 *
 */
package marbles

import akka.actor.{Actor, ActorRef}
import marbles.GameMaker.NextTurn
import marbles.Player.{GetScore, PlaceMarble, TurnEnd}

class Player(index: Int, var score: Long, marbleRing: ActorRef) extends Actor {

  override def receive: Receive = {
    case PlaceMarble(n) => marbleRing ! MarbleRing.AddMarble(n)
    case TurnEnd(turnScore) => {
      score = score + turnScore
      context.parent ! NextTurn
    }
    case GetScore => sender() ! GameMaker.Score(index, score)
  }
}



object Player {
  case class PlaceMarble(marbleNumber: Int)
  case class TurnEnd(turnScore: Int)
  case object GetScore
}
