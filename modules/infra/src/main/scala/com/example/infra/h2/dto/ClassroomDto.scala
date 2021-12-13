package com.example.infra.h2.dto

import com.example.entity.classroom.Classroom
import com.example.types.{ClassroomId, ClassroomName}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString

final case class ClassroomDto(
    cid: Int,
    cname: String,
) {
  def toClassroom: Classroom = {
    // 時間がなかったのでunsafeを使った
    val piCid: PosInt            = Refined.unsafeApply(cid)
    val nesCname: NonEmptyString = Refined.unsafeApply(cname)

    Classroom(ClassroomId(piCid), ClassroomName(nesCname))
  }
}
