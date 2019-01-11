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

import java.util.Date

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import marbles.GameMaker.{GameSetup, NextTurn, Score}
import marbles.Player.GetScore

class GameMaker extends Actor {

  private var ring: ActorRef = _
  private var playerz: Vector[ActorRef] = _
  private var marbleIndex: Int = 1
  private var totalMarbles: Int = _
  private var scores: List[Score] = List()

  override def receive: Receive = {
    case GameSetup(players, marbles) => {
      println(">>start " + new Date())

      ring = context.actorOf(
        Props(new MarbleRing(marbles)).withDispatcher("akka.actor.blocking-dispatcher"),
        "ringActor"
      )

      playerz = (1 to players).map(
        playerIndex => context.actorOf(Props(new Player(playerIndex, 0, ring)
        ).withDispatcher("akka.actor.player-dispatcher"))
      ).toVector
      totalMarbles = marbles
      context.become(ready)
      self ! NextTurn

    }
  }

  def ready: Receive = {
    case NextTurn =>
//      if (marbleIndex <= totalMarbles) {
//        if (this.playerTurns.isEmpty) {
//          this.playerTurns = playerz
//        }
//        val (nextPlayer, nextPlayerTurns) = (playerTurns.head, playerTurns.tail)
//        playerTurns = nextPlayerTurns
//        nextPlayer ! Player.PlaceMarble(marbleIndex)
//        marbleIndex = marbleIndex + 1
//        if (marbleIndex == totalMarbles) {
//          playerz.foreach(_ ! GetScore)
//        }
//      }

      if (marbleIndex < totalMarbles) {
        val nextPlayer: ActorRef = rotatePlayerTurn
        nextPlayer ! Player.PlaceMarble(marbleIndex)

        marbleIndex = marbleIndex + 1

      } else if (marbleIndex == totalMarbles) {
        playerz.foreach(_ ! GetScore)
      }

    case s:Score =>
      scores = s :: scores
      if (scores.length == playerz.length) {
        val winner = scores.maxBy(_.score)
        println(s">>>>>>> Winner is ${winner.player}: ${winner.score}")
        println(">>end " + new Date())
        self ! PoisonPill
      }
//465 players; last marble is worth 7194000 points => 3187566597

  }

  private def rotatePlayerTurn = {
    val (nextPlayer, nextPlayerTurns) = (playerz.head, playerz.tail)
    playerz = nextPlayerTurns :+ nextPlayer
    nextPlayer
  }
}

object GameMaker {
  case class GameSetup(players: Int, lastMarblePoints: Int)
  case object NextTurn
  case class Score(player: Int, score: Long)
}
