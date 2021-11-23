package com.example.infra.h2.dto

import com.example.types.{ClassroomId, ClassroomName, StudentId, StudentName}
import com.example.entity.studentBelongingToFacilitator.StudentBelongingToFacilitator
import eu.timepit.refined.api.RefType
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString
import cats.implicits._

final case class Student(
    id: Int,
    name: String,
    classroomId: Option[Int],
    classroomName: Option[String],
) {
  def toStudentBelongingToFacilitator: Either[String, StudentBelongingToFacilitator] =
    for {
      sid   <- RefType.applyRef[PosInt](id)
      sname <- RefType.applyRef[NonEmptyString](name)
      cid   <- classroomId.traverse(RefType.applyRef[PosInt](_))
      cname <- classroomName.traverse(RefType.applyRef[NonEmptyString](_))
      sf    <- StudentBelongingToFacilitator.create(
        StudentId(sid),
        StudentName(sname),
        cid.map(ClassroomId(_)),
        cname.map(ClassroomName(_))
      )
    } yield sf
}
