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

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import marbles.GameMaker.GameSetup

import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")



    val players = 465 //StdIn.readInt()
    val marbles = 7194000//StdIn.readInt()
    Console.println(s"Enter number of players=$players")
    Console.println(s"Enter number of marbles=$marbles")

    val config = ConfigFactory.load().withFallback(ConfigFactory.defaultReference(this.getClass.getClassLoader))
    println(config.getString("test"))
    println(config.getConfig("akka"))

    val system = ActorSystem.apply("actorSystem0", config)
    val gameMaker = system.actorOf(Props(new GameMaker()).withDispatcher("akka.actor.blocking-dispatcher"))

    Console.println("Enter To start")
    StdIn.readLine()


    gameMaker ! GameSetup(players, marbles)

  }
}