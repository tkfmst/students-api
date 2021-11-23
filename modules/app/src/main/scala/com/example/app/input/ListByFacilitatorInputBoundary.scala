package com.example.app.input

import com.example.app.input.ListByFacilitatorInput
import com.example.entity.studentBelongingToFacilitator.StudentBelongingToFacilitatorRepository
import com.example.app.output.ListByFacilitatorOutput

abstract class ListByFacilitatorInputBoundary[F[_]]() {
  val studentBelongingToFacilitatorRepo: StudentBelongingToFacilitatorRepository[F]
  def execute(input: ListByFacilitatorInput): F[ListByFacilitatorOutput]
}
