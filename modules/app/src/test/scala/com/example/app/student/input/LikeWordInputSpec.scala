package com.example.app.student.input

import cats._
import eu.timepit.refined.auto._

class LikeWordInputSpec extends munit.FunSuite {
  test("`select` is Only one `LikeWordInput` should be selected.") {
    val pm1: LikeWordInput = LikeWordInput.StudentName("a")
    val pm2: LikeWordInput = LikeWordInput.ClassroomName("b")

    val seq1: Seq[Option[Id[LikeWordInput]]] = Seq(
      None,
      Some(pm1),
      Some(pm2),
      None
    )
    assertEquals(LikeWordInput.select(seq1), Some(pm1))

    val seq2: Seq[Option[Id[LikeWordInput]]] = Seq(
      None,
      None,
      None,
      None
    )
    assertEquals(LikeWordInput.select(seq2), None)

  }
}
