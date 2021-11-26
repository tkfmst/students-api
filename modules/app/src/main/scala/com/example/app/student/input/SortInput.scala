package com.example.app.student.input

import com.example.entity.Sort

final case class SortInput(toSortKey: Sort.Key) {}
object SortInput                                {
  def fromString(value: String): Either[String, SortInput] =
    value.toLowerCase match {
      case "id"             => Right(SortInput(Sort.Key.StudentId))
      case "name"           => Right(SortInput(Sort.Key.StudentName))
      case "classroom.id"   => Right(SortInput(Sort.Key.ClassroomId))
      case "classroom.name" => Right(SortInput(Sort.Key.ClassroomName))
      case _                => Left(s"SortInput.fromString is Error: value=${value}")
    }

  val default: SortInput = SortInput(Sort.Key.StudentId)
}
