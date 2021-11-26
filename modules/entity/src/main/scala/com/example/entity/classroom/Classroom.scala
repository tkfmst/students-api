package com.example.entity.classroom

import com.example.types.{ClassroomId, ClassroomName}
import com.example.entity.ContainingWordFilter
import io.circe.generic.JsonCodec
import io.estatico.newtype.macros.newtype

@JsonCodec final case class Classroom(
    id: ClassroomId,
    name: ClassroomName,
) {}

object Classroom {
  @newtype final case class CList(ls: List[Classroom]) {
    def filterByIntermediateMatch(targetWord: Option[ContainingWordFilter]): List[Classroom] =
      targetWord match {
        case Some(ContainingWordFilter.ClassroomName(nes)) =>
          ls.filter(_.name.toNonEmptyString.value.contains(nes.value))
        case _                                             => ls
      }
  }
}
