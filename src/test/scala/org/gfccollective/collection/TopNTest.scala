package org.gfccollective.collection


import scala.math.Ordering
import org.scalatestplus.scalacheck.Checkers
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TopNTest extends AnyFunSuite with Matchers with Checkers {

  import scala.math.Ordering.{ String => StringOrdering }

  val TestItems = scala.util.Random.shuffle(('a' to 'z').map(_.toString)).toSeq

  test("FewerThanN") {
    val input = TestItems.take(3)
    val top5 = TopN(5, input)
    top5 should equal(input.sorted(StringOrdering))
  }

  test("FewerThanNReverseOrder") {
    val input = TestItems.take(3)
    val top5 = TopN(5, input)(StringOrdering.reverse)
    top5 should equal(input.sorted(StringOrdering.reverse))
  }

  test("ExactlyN") {
    val input = TestItems.take(5)
    val top5 = TopN(5, input)
    top5 should equal(input.sorted(StringOrdering))
  }

  test("ExactlyNReverseOrder") {
    val input = TestItems.take(5)
    val top5 = TopN(5, input)(StringOrdering.reverse)
    top5 should equal(input.sorted(StringOrdering.reverse))
  }

  test("TopN") {
    val top5 = TopN(5, TestItems)
    top5 should equal(TestItems.sorted(StringOrdering).take(5))
  }

  test("TopNReverseOrder") {
    val top5 = TopN(5, TestItems)(StringOrdering.reverse)
    top5 should equal(TestItems.sorted(StringOrdering.reverse).take(5))
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
    check { (input: Array[Long], i: Int) =>
      val n: Int = math.abs(i % 1000) + 1
      val topN = TopN(n, input){Ordering.Long.reverse}
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
