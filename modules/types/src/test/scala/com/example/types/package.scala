package com.example.types

import eu.timepit.refined.auto._
import io.circe.syntax._
import io.circe.parser._
import io.circe.literal._

class TypesSpec extends munit.FunSuite {
  test("`StudentId` should support json encode/decode") {
    assertEquals(StudentId(1).asJson, json"""1""")

    assertEquals(decode[StudentId]("1"), Right(StudentId(1)))
    assert(decode[StudentId]("0").isLeft)
    assert(decode[StudentId]("-1").isLeft)
    assert(decode[StudentId]("a").isLeft)
    assert(decode[StudentId]("").isLeft)
  }

  test("`StudentName` should support json encode/decode") {
    assertEquals(StudentName("a").asJson, json""""a"""")

    assertEquals(decode[StudentName](""""a""""), Right(StudentName("a")))
    assert(decode[StudentName]("1").isLeft)
    assert(decode[StudentName](""" "" """).isLeft)
  }

  test("`ClassroomId` should support json encode/decode") {
    assertEquals(ClassroomId(1).asJson, json"""1""")

    assertEquals(decode[ClassroomId]("1"), Right(ClassroomId(1)))
    assert(decode[ClassroomId]("0").isLeft)
    assert(decode[ClassroomId]("-1").isLeft)
    assert(decode[ClassroomId]("a").isLeft)
    assert(decode[ClassroomId]("").isLeft)
  }

  test("`ClassroomName` should support json encode/decode") {
    assertEquals(ClassroomName("a").asJson, json""""a"""")

    assertEquals(decode[ClassroomName](""""a""""), Right(ClassroomName("a")))
    assert(decode[ClassroomName]("1").isLeft)
    assert(decode[ClassroomName](""" "" """).isLeft)
  }

  test("`ListByFacilitatorCount` should support json encode/decode") {
    assertEquals(ListByFacilitatorCount(1).asJson, json"""1""")

    assertEquals(decode[ListByFacilitatorCount]("1"), Right(ListByFacilitatorCount(1)))
    assertEquals(decode[ListByFacilitatorCount]("0"), Right(ListByFacilitatorCount(0)))
    assertEquals(decode[ListByFacilitatorCount]("-1"), Right(ListByFacilitatorCount(-1)))
    assert(decode[ListByFacilitatorCount]("a").isLeft)
    assert(decode[ListByFacilitatorCount]("").isLeft)
  }
}
