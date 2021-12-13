package com.example.infra.h2

import cats.effect._
import cats.implicits._
import com.example.entity.{ContainingWordFilter, Paging, Sort}
import com.example.entity.classroom.Classroom
import com.example.entity.student.{Student, StudentRepository}
import com.example.infra.h2.dto.StudentDto
import com.example.types.ListByFacilitatorCount
import doobie.implicits._
import doobie.util.fragment.Fragment
import doobie.util.transactor.Transactor
import eu.timepit.refined.auto._

final case class StudentRepositoryOnH2[F[_]: MonadCancelThrow](xa: Transactor[F]) extends StudentRepository[F] {
  private[this] def frWhereFidIn(cs: List[Classroom]): Fragment =
    fr"WHERE classroom_id IN (" ++ cs.map(c => fr"${c.id.toPosInt.value}").intercalate(fr",") ++ fr")"

  private[this] def frAndLike(intermediateMatchFilter: Option[ContainingWordFilter]): Fragment =
    intermediateMatchFilter match {
      case Some(x) =>
        x match {
          case ContainingWordFilter.StudentName(target) => fr"AND student.name LIKE ${"%" + target.value + "%"}"
          case _                                        => fr""
        }
      case None    => fr""
    }

  private[this] def frSortOrder(paging: Paging): Fragment = {
    val frSort: Fragment = paging.sort.key match {
      case Sort.Key.StudentId     => fr"student.id"
      case Sort.Key.StudentName   => fr"student.name"
      case Sort.Key.ClassroomId   => fr"classroom.id"
      case Sort.Key.ClassroomName => fr"classroom.name"
    }

    val frOrder: Fragment = paging.sort.order match {
      case Sort.Order.Asc  => fr"ASC"
      case Sort.Order.Desc => fr"DESC"
    }

    fr"""ORDER BY""" ++ frSort ++ frOrder
  }

  private[this] def frLimit(paging: Paging): Fragment = fr"""Limit ${paging.limit.toPosInt.value}"""

  private[this] def frOffset(paging: Paging): Fragment =
    ((paging.page.toPosInt.value - 1) * paging.limit.toPosInt.value) match {
      case i if i <= 0 => fr""
      case i           => fr"OFFSET $i"
    }

  // TODO 名前が長い
  def getBelongingToClassroomsWithPagingAndFilter(
      cs: List[Classroom],
      paging: Paging,
      intermediateMatchFilter: Option[ContainingWordFilter]
  ): F[List[Student]] = {

    (fr"""
      SELECT
        id,
        name,
        classroom_id
      FROM student
    """
      ++ frWhereFidIn(cs)
      ++ frAndLike(intermediateMatchFilter)
      ++ frSortOrder(paging)
      ++ frLimit(paging)
      ++ frOffset(paging))
      .query[StudentDto]
      .stream
      .compile
      .toList
      .map { ls => ls.map(_.toStudent) }
      .transact(xa)
  }

  def getBelongingToClassroomsTotalCount(
      cs: List[Classroom],
      intermediateMatchFilter: Option[ContainingWordFilter]
  ): F[ListByFacilitatorCount] = {

    (fr"""
      SELECT
        COUNT(1)
      FROM  student
      """
      ++ frWhereFidIn(cs)
      ++ frAndLike(intermediateMatchFilter))
      .query[Int]
      .unique
      .map(ListByFacilitatorCount(_))
      .transact(xa)
  }
}
