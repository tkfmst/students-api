package com.example.app.student.input

import com.example.entity.student.StudentRepository
import com.example.entity.classroom.ClassroomRepository
import com.example.app.student.output.ListByFacilitatorOutput

abstract class ListByFacilitatorInputBoundary[F[_]]() {
  val classroomRepo: ClassroomRepository[F]
  val studentRepo: StudentRepository[F]
  def execute(input: ListByFacilitatorInput): F[ListByFacilitatorOutput]
}
