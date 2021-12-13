package com.example.api

import cats.implicits._
import com.example.app.student.input.{LikeWordInput, OrderInput, SortInput}
import com.example.types.{FacilitatorId, LimitPerPage, PageNumber}
import eu.timepit.refined.api.RefType
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.types.string.NonEmptyString
import org.http4s._
import org.http4s.dsl.io._

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

  implicit val sortInputQueryParamDecoder: QueryParamDecoder[SortInput] =
    QueryParamDecoder[String]
      .emap(s =>
        SortInput
          .fromString(s)
          .leftMap(msg => ParseFailure(s"sortInputQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  implicit val orderInputQueryParamDecoder: QueryParamDecoder[OrderInput] =
    QueryParamDecoder[String]
      .emap(s =>
        OrderInput
          .fromString(s)
          .leftMap(msg => ParseFailure(s"orderInputQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  implicit val likeWordInputStudentNameQueryParamDecoder: QueryParamDecoder[LikeWordInput.StudentName] =
    QueryParamDecoder[String]
      .emap(s =>
        RefType
          .applyRef[NonEmptyString](s)
          .map(LikeWordInput.studentName(_))
          .leftMap(msg => ParseFailure(s"likeWordInputStudentNameQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  implicit val likeWordInputClassroomNameQueryParamDecoder: QueryParamDecoder[LikeWordInput.ClassroomName] =
    QueryParamDecoder[String]
      .emap(s =>
        RefType
          .applyRef[NonEmptyString](s)
          .map(LikeWordInput.classroomName(_))
          .leftMap(msg => ParseFailure(s"likeWordInputClassroomNameQueryParamDecoder: ${msg}", s"For input: `${s}`"))
      )

  object FacilitatorIdInputParam extends ValidatingQueryParamDecoderMatcher[FacilitatorId]("facilitator_id")
  object PageNumberInputParam    extends OptionalValidatingQueryParamDecoderMatcher[PageNumber]("page")
  object LimitPerPageInputParam  extends OptionalValidatingQueryParamDecoderMatcher[LimitPerPage]("limit")
  object SortInputParam          extends OptionalValidatingQueryParamDecoderMatcher[SortInput]("sort")
  object OrderInputParam         extends OptionalValidatingQueryParamDecoderMatcher[OrderInput]("order")
  object LikeWordStudentNameInputParam
      extends OptionalValidatingQueryParamDecoderMatcher[LikeWordInput.StudentName]("name_like")
  object LikeWordClassroomNameInputParam
      extends OptionalValidatingQueryParamDecoderMatcher[LikeWordInput.ClassroomName]("classroom.name_like")
}
