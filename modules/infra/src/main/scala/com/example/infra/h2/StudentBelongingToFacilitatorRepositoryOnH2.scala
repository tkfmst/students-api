package com.example.infra.h2

import doobie.util.transactor.Transactor
import doobie.util.fragment.Fragment
import com.example.infra.h2.dto
import cats.effect._
import doobie.implicits._
import com.example.entity.studentBelongingToFacilitator.SortLogic.{SortKey, SortOrder}
import com.example.types.{FacilitatorId, ListByFacilitatorCount}
import com.example.entity.studentBelongingToFacilitator.{
  PageLimit,
  PartialMatch,
  SortLogic,
  StudentBelongingToFacilitator,
  StudentBelongingToFacilitatorRepository
}
import eu.timepit.refined.auto._

final case class StudentBelongingToFacilitatorRepositoryOnH2[F[_]: MonadCancelThrow](xa: Transactor[F])
    extends StudentBelongingToFacilitatorRepository[F] {

  def get(
      fid: FacilitatorId,
      page: PageLimit,
      sort: SortLogic,
      partialMatch: Option[PartialMatch],
  ): F[List[StudentBelongingToFacilitator]] = {

    val frSort: Fragment = sort.key match {
      case SortKey.StudentId     => fr"student.id"
      case SortKey.StudentName   => fr"student.name"
      case SortKey.ClassroomId   => fr"classroom.id"
      case SortKey.ClassroomName => fr"classroom.name"
    }

    val frOrder: Fragment = sort.order match {
      case SortOrder.Asc  => fr"ASC"
      case SortOrder.Desc => fr"DESC"
    }

    val frSortOrder: Fragment = fr"""ORDER BY""" ++ frSort ++ frOrder

    val frOffset: Fragment = ((page.page.toPosInt.value - 1) * page.limit.toPosInt.value) match {
      case i if i <= 0 => fr""
      case i           => fr"OFFSET $i"
    }

    val frLike: Fragment = partialMatch match {
      case Some(pm: PartialMatch) =>
        pm match {
          case PartialMatch.StudentName(nes)   => fr"AND student.name LIKE ${"%" + nes.value + "%"}"
          case PartialMatch.ClassroomName(nes) => fr"AND classroom.name LIKE ${"%" + nes.value + "%"}"
        }
      case None                   => fr""
    }

    val q = fr"""
      SELECT
        student.id,
        student.name,
        classroom.id,
        classroom.name
      FROM  facilitator
      INNER JOIN classroom_facilitator ON facilitator.id = classroom_facilitator.facilitator_id
      INNER JOIN classroom ON classroom_facilitator.classroom_id = classroom.id
      INNER JOIN student ON classroom.id = student.classroom_id
      WHERE facilitator.id = ${fid.toPosInt.value}
      """ ++
      frLike ++
      frSortOrder ++
      fr"""
      LIMIT ${page.limit.toPosInt.value}
      """ ++
      frOffset

    q.query[dto.Student]
      .stream
      .compile
      .toList
      .map(_.map(_.toStudentBelongingToFacilitator).collect { case Right(s) => s })
      .transact(xa)
  }

  def count(
      fid: FacilitatorId,
      partialMatch: Option[PartialMatch],
  ): F[ListByFacilitatorCount] = {
    val frLike: Fragment = partialMatch match {
      case Some(pm: PartialMatch) =>
        pm match {
          case PartialMatch.StudentName(nes)   => fr"AND student.name LIKE ${"%" + nes.value + "%"}"
          case PartialMatch.ClassroomName(nes) => fr"AND classroom.name LIKE ${"%" + nes.value + "%"}"
        }
      case None                   => fr""
    }

    val q = fr"""
      SELECT
        COUNT(1)
      FROM  facilitator
      INNER JOIN classroom_facilitator ON facilitator.id = classroom_facilitator.facilitator_id
      INNER JOIN classroom ON classroom_facilitator.classroom_id = classroom.id
      INNER JOIN student ON classroom.id = student.classroom_id
      WHERE facilitator.id = ${fid.toPosInt.value}
      """ ++
      frLike

    q.query[Int]
      .unique
      .map(ListByFacilitatorCount(_))
      .transact(xa)
  }
}
