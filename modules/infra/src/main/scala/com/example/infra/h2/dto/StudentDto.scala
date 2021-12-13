package com.example.infra.h2.dto

import com.example.entity.student.Student
import com.example.types.{ClassroomId, StudentId, StudentName}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString

final case class StudentDto(
    sid: Int,
    sname: String,
    cid: Option[Int],
) {
  def toStudent: Student = {
    // 時間がなかったのでunsafeを使った
    val piSid: PosInt            = Refined.unsafeApply(sid)
    val nesSname: NonEmptyString = Refined.unsafeApply(sname)
    val piCid: Option[PosInt]    = cid.map(Refined.unsafeApply(_))

    Student(StudentId(piSid), StudentName(nesSname), piCid.map(ClassroomId(_)))
  }
}
