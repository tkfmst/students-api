package com.example.api

import eu.timepit.refined.types.string.NonEmptyString
import cats.implicits._
import com.example.types.FacilitatorId
import org.http4s._, org.http4s.dsl.io._
import eu.timepit.refined.api.RefType
import eu.timepit.refined.types.numeric.PosInt
import com.example.entity.studentBelongingToFacilitator.PageLimit.{LimitPerPage, PageNumber}
import com.example.entity.studentBelongingToFacilitator.SortLogic.{SortKey, SortOrder}
import com.example.entity.studentBelongingToFacilitator.PartialMatch

object Param {
  implicit val facilitatorIdQueryParamDecoder: QueryParamDecoder[FacilitatorId] =
    QueryParamDecoder[Int]
      .emap(i =>
        RefType
          .applyRef[PosInt](i)
          .map(FacilitatorId(_))
          .leftMap(msg => ParseFailure(s"facilitatorIdQueryParamDecoder: ${msg}", s"For input: `${i.toString}`"))
      )

  implicit val pageNumberQueryParamDecoder: QueryParamDecoder[PageNumber] =
    QueryParamDecoder[Int]
      .emap(i =>
        RefType
          .applyRef[PosInt](i)
          .map(PageNumber(_))
          .leftMap(msg => ParseFailure(s"pageNumberQueryParamDecoder: ${msg}", s"For input: `${i.toString}`"))
      )

  implicit val limitPerPageQueryParamDecoder: QueryParamDecoder[LimitPerPage] =
    QueryParamDecoder[Int]
      .emap(i =>
        RefType
          .applyRef[PosInt](i)
          .map(LimitPerPage(_))
          .leftMap(msg => ParseFailure(s"limitPerPageQueryParamDecoder: ${msg}", s"For input: `${i.toString}`"))
      )

  implicit val sortKeyQueryParamDecoder: QueryParamDecoder[SortKey] =
    QueryParamDecoder[String]
      .emap(s =>
        SortKey
          .fromString(s)
          .leftMap(msg => ParseFailure(s"sortKeyQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  implicit val sortOrderQueryParamDecoder: QueryParamDecoder[SortOrder] =
    QueryParamDecoder[String]
      .emap(s =>
        SortOrder
          .fromString(s)
          .leftMap(msg => ParseFailure(s"sortOrderQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  implicit val partialMatchStudentNameQueryParamDecoder: QueryParamDecoder[PartialMatch.StudentName] =
    QueryParamDecoder[String]
      .emap(s =>
        RefType
          .applyRef[NonEmptyString](s)
          .map(PartialMatch.StudentName(_))
          .leftMap(msg => ParseFailure(s"partialMatchStudentNameQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  implicit val partialMatchClassroomNameQueryParamDecoder: QueryParamDecoder[PartialMatch.ClassroomName] =
    QueryParamDecoder[String]
      .emap(s =>
        RefType
          .applyRef[NonEmptyString](s)
          .map(PartialMatch.ClassroomName(_))
          .leftMap(msg => ParseFailure(s"partialMatchClassroomNameQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  object FacilitatorIdParam extends ValidatingQueryParamDecoderMatcher[FacilitatorId]("facilitator_id")
  object PageNumberParam    extends OptionalValidatingQueryParamDecoderMatcher[PageNumber]("page")
  object LimitPerPageParam  extends OptionalValidatingQueryParamDecoderMatcher[LimitPerPage]("limit")
  object SortKeyParam       extends OptionalValidatingQueryParamDecoderMatcher[SortKey]("sort")
  object SortOrderParam     extends OptionalValidatingQueryParamDecoderMatcher[SortOrder]("order")
  object PartialMatchStudentNameParam
      extends OptionalValidatingQueryParamDecoderMatcher[PartialMatch.StudentName]("name_like")
  object PartialMatchClassroomNameParam
      extends OptionalValidatingQueryParamDecoderMatcher[PartialMatch.ClassroomName]("classroom.name_like")
}
