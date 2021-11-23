package com.example.app.usecase

import cats._
import cats.implicits._
import com.example.app.input.ListByFacilitatorInput
import com.example.app.output.ListByFacilitatorOutput
import com.example.entity.studentBelongingToFacilitator.{PageLimit, SortLogic, StudentBelongingToFacilitatorRepository}
import com.example.app.input.ListByFacilitatorInputBoundary

final case class ListByFacilitatorUsecaseInteractor[F[_]: FlatMap](
    studentBelongingToFacilitatorRepo: StudentBelongingToFacilitatorRepository[F]
) extends ListByFacilitatorInputBoundary[F] {
  def execute(input: ListByFacilitatorInput): F[ListByFacilitatorOutput] = {
    val pageLimit    = PageLimit(input.page, input.limit)
    val sortLogic    = SortLogic(input.sort, input.order)
    val partialMatch = input.like

    for {
      students <- studentBelongingToFacilitatorRepo.get(input.fid, pageLimit, sortLogic, partialMatch)
      count    <- studentBelongingToFacilitatorRepo.count(input.fid, partialMatch)
    } yield ListByFacilitatorOutput(students, count)
  }
}
