package org.gfccollective.collection


import scala.math.Ordering
import org.scalatestplus.scalacheck.Checkers
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable

class TopNTest extends AnyFunSuite with Matchers with Checkers {

  val TestItems: immutable.Seq[String] = scala.util.Random.shuffle(('a' to 'z').map(_.toString)).toSeq

  test("FewerThanN") {
    import scala.math.Ordering.{ String => StringOrdering }
    val input = TestItems.take(3)
    val top5 = TopN(5, input)
    top5 should equal(input.sorted)
  }

  test("FewerThanNReverseOrder") {
    implicit val org = scala.math.Ordering.String.reverse
    val input = TestItems.take(3)
    val top5 = TopN(5, input)
    top5 should equal(input.sorted)
  }

  test("ExactlyN") {
    import scala.math.Ordering.{ String => StringOrdering }
    val input = TestItems.take(5)
    val top5 = TopN(5, input)
    top5 should equal(input.sorted)
  }

  test("ExactlyNReverseOrder") {
    implicit val org = scala.math.Ordering.String.reverse
    val input = TestItems.take(5)
    val top5 = TopN(5, input)
    top5 should equal(input.sorted)
  }

  test("TopN") {
    import scala.math.Ordering.{ String => StringOrdering }
    val top5 = TopN(5, TestItems)
    top5 should equal(TestItems.sorted.take(5))
  }

  test("TopNReverseOrder") {
    implicit val org = scala.math.Ordering.String.reverse
    val top5 = TopN(5, TestItems)
    top5 should equal(TestItems.sorted.take(5))
  }

  test("RandomizedInputArrayInt") {
    check { (input: Array[Int], i: Int) =>
      val n: Int = math.abs(i % 1000) + 1
      val topN = TopN(n, input)
      val expected = input.toSeq.sorted.take(n)
      topN == expected
    }
  }

  test("RandomizedInputArrayLongReverse") {
    implicit val org = scala.math.Ordering.Long.reverse
    check { (input: Array[Long], i: Int) =>
      val n: Int = math.abs(i % 1000) + 1
      val topN = TopN(n, input)
      val expected = input.toSeq.sorted(Ordering.Long.reverse).take(n)
      topN == expected
    }
  }

  test("RandomizedInputListString") {
    check { (input: List[String], i: Int) =>
      val n: Int = math.abs(i % 1000) + 1
      val topN = TopN(n, input)
      val expected = input.toSeq.sorted.take(n)
      topN == expected
    }
  }
}
