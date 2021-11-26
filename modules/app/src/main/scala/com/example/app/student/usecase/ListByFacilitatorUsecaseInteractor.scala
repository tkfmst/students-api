package com.example.app.student.usecase

import cats._
import cats.implicits._
import com.example.app.student.input.ListByFacilitatorInput
import com.example.app.student.output.ListByFacilitatorOutput
import com.example.entity.student.StudentRepository
import com.example.entity.classroom.{Classroom, ClassroomRepository}
import com.example.types.ClassroomId
import com.example.app.student.input.ListByFacilitatorInputBoundary

final case class ListByFacilitatorUsecaseInteractor[F[_]: FlatMap](
    classroomRepo: ClassroomRepository[F],
    studentRepo: StudentRepository[F],
) extends ListByFacilitatorInputBoundary[F] {

  /**
   * HACK: データ数によっては一括でDMLで取ってくるようか？ or ドキュメント指向DBとか？
   */
  def execute(input: ListByFacilitatorInput): F[ListByFacilitatorOutput] =
    for {
      filteredClassrooms <- classroomRepo
        .getByAssignedFacilitator(input.fid)
        .map(classrooms => Classroom.CList(classrooms).filterByIntermediateMatch(input.toContainingWordFilter))
      students           <- studentRepo.getBelongingToClassroomsWithPagingAndFilter(
        filteredClassrooms,
        input.toPaging,
        input.toContainingWordFilter
      )
      totalCount         <- studentRepo.getBelongingToClassroomsTotalCount(
        filteredClassrooms,
        input.toContainingWordFilter
      )
    } yield {
      val cMap: Map[ClassroomId, Classroom] = filteredClassrooms.map(c => c.id -> c).toMap
      ListByFacilitatorOutput.build(cMap, students, totalCount)
    }
}
