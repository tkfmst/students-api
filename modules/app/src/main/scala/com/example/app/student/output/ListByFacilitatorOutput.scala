package com.example.app.student.output

import com.example.entity.classroom.Classroom
import com.example.entity.student.Student
import com.example.types.{ClassroomId, ListByFacilitatorCount, StudentId, StudentName}
import io.circe.generic.JsonCodec

@JsonCodec final case class ListByFacilitatorOutput(
    students: Seq[ListByFacilitatorOutput.OutputStudent],
    totalCount: ListByFacilitatorCount
)
object ListByFacilitatorOutput {
  @JsonCodec final case class OutputStudent(
      id: StudentId,
      name: StudentName,
      classroom: Classroom
  )

  def build(
      cMap: Map[ClassroomId, Classroom],
      students: List[Student],
      allTargetStudentsCount: ListByFacilitatorCount
  ): ListByFacilitatorOutput = {
    ListByFacilitatorOutput(
      students
        .map(s =>
          s.classroomId match {
            case Some(cid) => cMap.get(cid).map(c => OutputStudent(s.id, s.name, c))
            case _         => None
          }
        )
        .flatten,
      allTargetStudentsCount
    )
  }
}
