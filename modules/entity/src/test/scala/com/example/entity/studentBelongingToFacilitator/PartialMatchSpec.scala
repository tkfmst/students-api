package com.example.entity.studentBelongingToFacilitator

import eu.timepit.refined.auto._
import cats._

class PartialMatchSpec extends munit.FunSuite {
  test("`select` is Only one `PartialMatch` should be selected.") {
    val pm1: PartialMatch = PartialMatch.StudentName("a")
    val pm2: PartialMatch = PartialMatch.ClassroomName("b")

    val seq1: Seq[Option[Id[PartialMatch]]] = Seq(
      None,
      Some(pm1),
      Some(pm2),
      None
    )
    assertEquals(PartialMatch.select(seq1), Some(pm1))

    val seq2: Seq[Option[Id[PartialMatch]]] = Seq(
      None,
      None,
      None,
      None
    )
    assertEquals(PartialMatch.select(seq2), None)

  }
}
