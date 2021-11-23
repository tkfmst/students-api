package com.example.app.input

import com.example.types.FacilitatorId
import com.example.entity.studentBelongingToFacilitator.PageLimit.{LimitPerPage, PageNumber}
import com.example.entity.studentBelongingToFacilitator.SortLogic.{SortKey, SortOrder}
import com.example.entity.studentBelongingToFacilitator.PartialMatch

final case class ListByFacilitatorInput(
    fid: FacilitatorId,
    page: PageNumber,
    limit: LimitPerPage,
    sort: SortKey,
    order: SortOrder,
    like: Option[PartialMatch],
)

object ListByFacilitatorInput {
  def apply(
      fid: FacilitatorId,
      optPage: Option[PageNumber],
      optLimit: Option[LimitPerPage],
      optSort: Option[SortKey],
      optOrder: Option[SortOrder],
      optPartialMatch: Option[PartialMatch],
  ): ListByFacilitatorInput = ListByFacilitatorInput(
    fid,
    optPage.getOrElse(PageNumber.default),
    optLimit.getOrElse(LimitPerPage.default),
    optSort.getOrElse(SortKey.default),
    optOrder.getOrElse(SortOrder.default),
    optPartialMatch,
  )
}
