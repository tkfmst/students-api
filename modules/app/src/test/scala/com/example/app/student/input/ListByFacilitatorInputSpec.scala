package com.example.app.student.input

import eu.timepit.refined.auto._
import com.example.types.{FacilitatorId, LimitPerPage, PageNumber}

class ListByFacilitatorInputSpec extends munit.FunSuite {
  test("`ListByFacilitatorInput` should use the default value if `None` args are given at creation time.") {
    val anyFid = FacilitatorId(1)

    val input = ListByFacilitatorInput(
      fid = anyFid,
      optPage = None,
      optLimit = None,
      optSort = None,
      optOrder = None,
      optPartialMatch = None
    )

    assertEquals(input.page, PageNumber.default)
    assertEquals(input.limit, LimitPerPage.default)
    assertEquals(input.sort, SortInput.default)
    assertEquals(input.order, OrderInput.default)
    assertEquals(input.like, None)
  }
}
