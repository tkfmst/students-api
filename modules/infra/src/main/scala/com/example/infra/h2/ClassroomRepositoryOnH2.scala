package com.example.infra.h2

import cats.effect._
import com.example.entity.classroom.{Classroom, ClassroomRepository}
import com.example.infra.h2.dto.ClassroomDto
import com.example.types.FacilitatorId
import doobie.implicits._
import doobie.util.transactor.Transactor
import eu.timepit.refined.auto._

final case class ClassroomRepositoryOnH2[F[_]: MonadCancelThrow](xa: Transactor[F]) extends ClassroomRepository[F] {
  def getByAssignedFacilitator(fid: FacilitatorId): F[List[Classroom]] = {
    sql"""
      SELECT
        classroom.id,
        classroom.name
      FROM classroom_facilitator
      INNER JOIN classroom ON classroom_facilitator.classroom_id = classroom.id
      WHERE
        classroom_facilitator.facilitator_id = ${fid.toPosInt.value}
    """
      .query[ClassroomDto]
      .stream
      .compile
      .toList
      .map { ls => ls.map(_.toClassroom) }
      .transact(xa)
  }
}
