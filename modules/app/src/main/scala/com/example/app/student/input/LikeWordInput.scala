package com.example.app.student.input

import com.example.entity.ContainingWordFilter
import eu.timepit.refined.types.string.NonEmptyString

abstract sealed class LikeWordInput(val toFilter: ContainingWordFilter) extends Product with Serializable
object LikeWordInput {

  final case class StudentName(toNes: NonEmptyString)   extends LikeWordInput(ContainingWordFilter.StudentName(toNes))
  final case class ClassroomName(toNes: NonEmptyString) extends LikeWordInput(ContainingWordFilter.ClassroomName(toNes))

  def studentName(nes: NonEmptyString): StudentName     = LikeWordInput.StudentName(nes)
  def classroomName(nes: NonEmptyString): ClassroomName = LikeWordInput.ClassroomName(nes)

  /**
   * 部分一致検索要素が複数の場合でも使われるのは1つのみ
   *
   * 選び方は適当
   */
  def select[F[_]](seq: Seq[Option[F[LikeWordInput]]]): Option[F[LikeWordInput]] =
    seq.collectFirst { case Some(a) => a }
}
