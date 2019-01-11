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

class Ring {


  var ring = Vector[Int](0)
  private var focusIndex = 0

  def add(marble: Int): Unit = {

    val (left, right) = ring.splitAt(((focusIndex + 1) % ring.size) + 1)
    val newLeft = left :+ marble
    ring =  newLeft ++ right
    focusIndex = newLeft.size - 1
    ()
  }

  def printRing() = println(ring)

  def focusMarble: Int = ring(focusIndex)

  def remove7thBehind:Int = {
    var removedIndex = focusIndex - 7
    if (removedIndex < 0)
      removedIndex = ring.size + removedIndex
    val removed = ring(removedIndex)
    val newLeft = ring.take(removedIndex)
    val newRight = ring.drop(removedIndex + 1)
    focusIndex = newLeft.size
    ring = newLeft ++ newRight
    removed
  }

}
