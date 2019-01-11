package marbles

class Ring2 {


  var ring = Vector[Int](0)
  private var focusIndex = 0

  def add(marble: Int): Unit = {
//    0
//    0 1 <-
//    1 0
//    1 0 2 <-
//    0 2 1 (3) <-
//    2 1 (3) 0 (4) <-
//    1 3 0 4 2 (5) <-
//    3 0 4 [2] 5 1 (6)

    //3 0 4 [2] 5 1 (6)
    //5 1 (6) 3 0 4 [2]
    //(5) 1 6 3 0 4
    //1 6 3 0 4 (5)
    //6 3 0 4 (5) 1

    rotateLeft
    ring = ring :+ marble
    ()

  }



  def printRing() = println(ring)

  def focusMarble: Int = ring.last

  def remove7thBehind:Int = {

    (1 to 7).foreach { _ =>
      rotateRight
    }
    val r = ring.last
    ring = ring.init
    rotateLeft
    r

  }

  private def rotateLeft = {
    val head = ring.head
    ring = ring.tail :+ head
  }
  private def rotateRight = {
    val last = ring.last
    ring = last +: ring.init
  }
}
