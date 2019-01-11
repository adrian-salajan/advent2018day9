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

import akka.actor.Actor
import marbles.MarbleRing.AddMarble
import marbles.Player.TurnEnd

class MarbleRing(capacity: Int) extends Actor {

  var ring = new Ring2()
  override def receive: Receive = {

    case AddMarble(marbleNumber) =>
      if (marbleNumber % 23 == 0) {
        val playerScore = marbleNumber + ring.remove7thBehind
        sender() ! TurnEnd(playerScore)
      } else {
        ring.add(marbleNumber)
        sender() ! TurnEnd(0)
      }
  } //receive
}

object MarbleRing {
  case class AddMarble(marbleNumber: Int)

}
