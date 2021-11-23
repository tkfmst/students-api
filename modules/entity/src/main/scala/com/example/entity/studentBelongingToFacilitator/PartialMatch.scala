package com.example.entity.studentBelongingToFacilitator

import eu.timepit.refined.types.string.NonEmptyString

sealed abstract class PartialMatch() extends Product with Serializable {
  val value: NonEmptyString
}
object PartialMatch {
  final case class StudentName(value: NonEmptyString)   extends PartialMatch
  final case class ClassroomName(value: NonEmptyString) extends PartialMatch

  /**
   * 部分一致検索要素が複数の場合でも使われるのは1つのみ
   *
   * 選び方は適当
   */
  def select[F[_]](seq: Seq[Option[F[PartialMatch]]]): Option[F[PartialMatch]] =
    seq.collectFirst { case Some(a) => a }
}
