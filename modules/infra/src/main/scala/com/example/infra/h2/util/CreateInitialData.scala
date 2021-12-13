package com.example.infra.h2.util

import cats.effect._
import doobie.ConnectionIO
import doobie.h2._
import doobie.implicits._

object CreateInitialData {

  /**
   *   - 1: 1クラス-1生徒
   *   - 2: 1クラス-2生徒
   *   - 3: 2クラス-3生徒
   *   - 4: 0クラス-0生徒
   */
  val insertFacilitator: ConnectionIO[Int] =
    sql"""
      INSERT INTO facilitator VALUES(1);
      INSERT INTO facilitator VALUES(2);
      INSERT INTO facilitator VALUES(3);
      INSERT INTO facilitator VALUES(4);
    """.update.run

  val insertClassroom: ConnectionIO[Int] =
    sql"""
      INSERT INTO classroom VALUES(1, '一般クラス');
      INSERT INTO classroom VALUES(2, '特進クラス');
    """.update.run

  val insertStudent: ConnectionIO[Int] =
    sql"""
      INSERT INTO student VALUES(1, '生徒1', 1);
      INSERT INTO student VALUES(2, 'Student2', 2);
      INSERT INTO student VALUES(3, '生徒 その3', 2);
      INSERT INTO student VALUES(4, '生徒4所属クラスなし', null);
    """.update.run

  val insertClassroomFacilitator: ConnectionIO[Int] =
    sql"""
      INSERT INTO classroom_facilitator VALUES(1, 1);
      INSERT INTO classroom_facilitator VALUES(2, 2);
      INSERT INTO classroom_facilitator VALUES(1, 3);
      INSERT INTO classroom_facilitator VALUES(2, 3);
    """.update.run

  def run[F[_]: MonadCancelThrow](xa: H2Transactor[F]): F[Unit] =
    (for {
      f  <- insertFacilitator
      c  <- insertClassroom
      s  <- insertStudent
      cf <- insertClassroomFacilitator
    } yield ()).transact(xa)
}
