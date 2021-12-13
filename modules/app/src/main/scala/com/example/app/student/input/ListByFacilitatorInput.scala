package com.example.app.student.input

import com.example.entity.{ContainingWordFilter, Paging, Sort}
import com.example.types.{FacilitatorId, LimitPerPage, PageNumber}

final case class ListByFacilitatorInput(
    fid: FacilitatorId,
    page: PageNumber,
    limit: LimitPerPage,
    sort: SortInput,
    order: OrderInput,
    like: Option[LikeWordInput],
) {
  def toContainingWordFilter: Option[ContainingWordFilter] = like.map(_.toFilter)

  def toPaging: Paging = Paging(page, limit, Sort(sort.toSortKey, order.toSortOrder))
}

object ListByFacilitatorInput {
  def apply(
      fid: FacilitatorId,
      optPage: Option[PageNumber],
      optLimit: Option[LimitPerPage],
      optSort: Option[SortInput],
      optOrder: Option[OrderInput],
      optPartialMatch: Option[LikeWordInput],
  ): ListByFacilitatorInput = ListByFacilitatorInput(
    fid,
    optPage.getOrElse(PageNumber.default),
    optLimit.getOrElse(LimitPerPage.default),
    optSort.getOrElse(SortInput.default),
    optOrder.getOrElse(OrderInput.default),
    optPartialMatch,
  )
}
