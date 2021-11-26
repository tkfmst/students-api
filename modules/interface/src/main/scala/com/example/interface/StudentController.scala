package com.example.interface

import com.example.app.student.output.ListByFacilitatorOutput
import com.example.app.student.input.{ListByFacilitatorInput, ListByFacilitatorInputBoundary}

final case class StudentController[F[_]](listByFacilitatorUsecase: ListByFacilitatorInputBoundary[F]) {
  def listByFacilitator(input: ListByFacilitatorInput): F[ListByFacilitatorOutput] =
    listByFacilitatorUsecase.execute(input)
}
