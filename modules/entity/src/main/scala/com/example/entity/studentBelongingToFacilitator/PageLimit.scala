package com.example.entity.studentBelongingToFacilitator

import eu.timepit.refined.types.numeric.PosInt
import io.estatico.newtype.macros.newtype
import com.example.entity.studentBelongingToFacilitator.PageLimit.{LimitPerPage, PageNumber}
import eu.timepit.refined.auto._

final case class PageLimit(page: PageNumber, limit: LimitPerPage)
object PageLimit {
  @newtype case class PageNumber(toPosInt: PosInt)
  object PageNumber {
    val default: PageNumber = PageNumber(1)
  }

  @newtype case class LimitPerPage(toPosInt: PosInt)
  object LimitPerPage {
    val default: LimitPerPage = LimitPerPage(10)
  }
}
