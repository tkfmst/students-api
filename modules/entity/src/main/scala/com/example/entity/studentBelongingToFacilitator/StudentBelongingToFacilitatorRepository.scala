package com.example.entity.studentBelongingToFacilitator

import com.example.types.{FacilitatorId, ListByFacilitatorCount}

abstract class StudentBelongingToFacilitatorRepository[F[_]]() {
  def get(
      fid: FacilitatorId,
      page: PageLimit,
      sort: SortLogic,
      partialMatch: Option[PartialMatch],
  ): F[List[StudentBelongingToFacilitator]]

  def count(
      fid: FacilitatorId,
      partialMatch: Option[PartialMatch],
  ): F[ListByFacilitatorCount]
}
