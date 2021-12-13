package com.example.entity.student

import com.example.entity.{ContainingWordFilter, Paging}
import com.example.entity.classroom.Classroom
import com.example.types.ListByFacilitatorCount

abstract class StudentRepository[F[_]]() {
  def getBelongingToClassroomsWithPagingAndFilter(
      cs: List[Classroom],
      paging: Paging,
      intermediateMatchFilter: Option[ContainingWordFilter]
  ): F[List[Student]]

  def getBelongingToClassroomsTotalCount(
      cs: List[Classroom],
      intermediateMatchFilter: Option[ContainingWordFilter]
  ): F[ListByFacilitatorCount]
}
