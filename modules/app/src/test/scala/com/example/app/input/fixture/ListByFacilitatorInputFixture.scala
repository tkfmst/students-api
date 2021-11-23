package com.example.app.input
package fixture

import eu.timepit.refined.auto._
import com.example.types._
import com.example.entity.studentBelongingToFacilitator.PageLimit.{LimitPerPage, PageNumber}
import com.example.entity.studentBelongingToFacilitator.SortLogic.{SortKey, SortOrder}
import com.example.entity.studentBelongingToFacilitator._

object ListByFacilitatorInputFixture {
  val default: ListByFacilitatorInput =
    ListByFacilitatorInput(
      fid = FacilitatorId(1),
      page = PageNumber(1),
      limit = LimitPerPage(3),
      sort = SortKey.StudentId,
      order = SortOrder.Asc,
      like = Some(PartialMatch.StudentName("b")),
    )
}
