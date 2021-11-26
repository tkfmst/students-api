package com.example.entity

import eu.timepit.refined.types.string.NonEmptyString

sealed abstract class ContainingWordFilter() extends Product with Serializable {
  val value: NonEmptyString
}
object ContainingWordFilter {
  final case class StudentName(value: NonEmptyString)   extends ContainingWordFilter
  final case class ClassroomName(value: NonEmptyString) extends ContainingWordFilter
}
