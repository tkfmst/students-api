package com.example.entity.studentBelongingToFacilitator

import com.example.entity.studentBelongingToFacilitator.SortLogic.{SortKey, SortOrder}

final case class SortLogic(key: SortKey, order: SortOrder)
object SortLogic {
  sealed abstract class SortKey(val value: String) extends Product with Serializable
  object SortKey {
    case object StudentId     extends SortKey(value = "id")
    case object StudentName   extends SortKey(value = "name")
    case object ClassroomId   extends SortKey(value = "classroom.id")
    case object ClassroomName extends SortKey(value = "classroom.name")

    val default: SortKey = StudentId

    def fromString(value: String): Either[String, SortKey] =
      value match {
        case StudentId.value     => Right(StudentId)
        case StudentName.value   => Right(StudentName)
        case ClassroomId.value   => Right(ClassroomId)
        case ClassroomName.value => Right(ClassroomName)
        case _                   => Left(s"SortKey.fromString is Error: value=${value}")
      }
  }

  sealed abstract class SortOrder(val value: String) extends Product with Serializable
  object SortOrder {
    case object Asc  extends SortOrder(value = "asc")
    case object Desc extends SortOrder(value = "desc")

    val default: SortOrder = Asc

    def fromString(value: String): Either[String, SortOrder] =
      value match {
        case Asc.value  => Right(Asc)
        case Desc.value => Right(Desc)
        case _          => Left(s"SortOrder.fromString is Error: value=${value}")
      }
  }
}
