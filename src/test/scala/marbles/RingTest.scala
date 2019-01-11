package marbles

import org.scalatest.{FunSuite, Matchers}

class RingTest extends FunSuite with Matchers {


  test("add correctly") {
    val ring = new Ring2()
    (1 to 11).foreach(ring.add)

    //ring.ring shouldBe Vector(0, 8, 4, 9, 2, 10, 5, 11, 1, 6, 3, 7)

    ring.focusMarble shouldBe 11

    ring.remove7thBehind shouldBe 0
  }

  test("add correctly 2") {
    val ring = new Ring2()
    (1 to 22).foreach(ring.add)

    //ring.ring shouldBe Vector(0, 16,  8, 17,  4, 18,  9, 19,  2, 20, 10, 21,  5, 22,11,  1, 12,  6, 13,  3, 14,  7, 15 )

    ring.focusMarble shouldBe 22

    ring.remove7thBehind shouldBe 9

    //ring.ring shouldBe Vector(0, 16,  8, 17,  4, 18, 19,  2, 20, 10, 21,  5, 22,11,  1, 12,  6, 13,  3, 14,  7, 15 )

    ring.focusMarble shouldBe 19

  }


}
