package com.example.infra.h2.util

import doobie.h2._
import cats.effect._
import doobie.ConnectionIO
import doobie.implicits._

/**
 * 手抜きでDB作成する用
 */
/**
 *   - 固定データしか入れないのでAUTO_INCREMENTは設定しない
 *
 *   - B Tree Indexは中間・後方一致には効かないが一応作る
 */
object RebuildTable {

  // Table: facilitator

  val dropFacilitator: ConnectionIO[Int] =
    sql"""
      DROP TABLE IF EXISTS facilitator
    """.update.run

  val createFacilitator: ConnectionIO[Int] =
    sql"""
      CREATE TABLE facilitator (
        id INT NOT NULL,
        PRIMARY KEY(id)
      )
    """.update.run

  // Table: classroom

  val dropClassroom: ConnectionIO[Int] =
    sql"""
      DROP TABLE IF EXISTS classroom 
    """.update.run

  val createClassroom: ConnectionIO[Int] =
    sql"""
      CREATE TABLE classroom(
        id INT NOT NULL,
        name VARCHAR(255) NOT NULL,
        PRIMARY KEY(id)
      )
    """.update.run

  val createClassroomIndex: ConnectionIO[Int] =
    sql"""
      CREATE INDEX ON classroom(name);
    """.update.run

  // Table: student

  val dropStudent: ConnectionIO[Int] =
    sql"""
      DROP TABLE IF EXISTS student 
    """.update.run

  val createStudent: ConnectionIO[Int] =
    // classroom_idはnull許容
    sql"""
      CREATE TABLE student (
        id INT NOT NULL,
        name VARCHAR(255) NOT NULL,
        classroom_id INT,
        PRIMARY KEY(id)
      )
    """.update.run

  val createStudentIndex: ConnectionIO[Int] =
    sql"""
      CREATE INDEX ON student(name);
      CREATE INDEX ON student(classroom_id);
      ALTER TABLE student ADD CONSTRAINT fk__student__classroom_id__classroom__id FOREIGN KEY (classroom_id) REFERENCES classroom(id);
    """.update.run

  // Table: classroom_facilitator

  val dropClassroomFacilitator: ConnectionIO[Int] =
    sql"""
      DROP TABLE IF EXISTS classroom_facilitator
    """.update.run

  val createClassroomFacilitator: ConnectionIO[Int] =
    sql"""
      CREATE TABLE classroom_facilitator(
        classroom_id INT NOT NULL,
        facilitator_id INT NOT NULL
      )
    """.update.run

  val createClassroomFacilitatorIndex: ConnectionIO[Int] =
    sql"""
      CREATE INDEX ON classroom_facilitator(classroom_id);
      CREATE INDEX ON classroom_facilitator(facilitator_id);
      ALTER TABLE classroom_facilitator ADD UNIQUE (classroom_id, facilitator_id);
      ALTER TABLE classroom_facilitator ADD CONSTRAINT fk__classroom_facilitator__classroom_id__classroom__id FOREIGN KEY (classroom_id) REFERENCES classroom(id);
      ALTER TABLE classroom_facilitator ADD CONSTRAINT fk__classroom_facilitator__facilitator_id__facilitator__id FOREIGN KEY (facilitator_id) REFERENCES facilitator(id);
    """.update.run

  def run[F[_]: MonadCancelThrow](xa: H2Transactor[F]): F[Unit] =
    (for {
      df   <- dropFacilitator
      cf   <- createFacilitator
      dc   <- dropClassroom
      cc   <- createClassroom
      cci  <- createClassroomIndex
      ds   <- dropStudent
      cs   <- createStudent
      csi  <- createStudentIndex
      dcf  <- dropClassroomFacilitator
      ccf  <- createClassroomFacilitator
      ccfi <- createClassroomFacilitatorIndex
    } yield ()).transact(xa)
}
