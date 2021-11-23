package com.example.entity.studentBelongingToFacilitator

import com.example.types.{ClassroomId, ClassroomName, StudentId, StudentName}
import io.circe.generic.JsonCodec

@JsonCodec final case class StudentBelongingToFacilitator(
    id: StudentId,
    name: StudentName,
    classroom: Option[StudentBelongingToFacilitator.PartlyClassroom],
)
object StudentBelongingToFacilitator {
  def create(
      sId: StudentId,
      sName: StudentName,
      cId: Option[ClassroomId],
      cName: Option[ClassroomName],
  ): Either[String, StudentBelongingToFacilitator] = (cId, cName) match {
    case (Some(id), Some(name)) => Right(StudentBelongingToFacilitator(sId, sName, Some(PartlyClassroom(id, name))))
    case _                      => Left(s"Invalid classroom parameter: cId=${cId.toString}, cName=${cName.toString}")
  }

  @JsonCodec final case class PartlyClassroom(
      id: ClassroomId,
      name: ClassroomName,
  ) {}
}
