package com.example.infra.h2.util

import cats.implicits._
// import doobie._
import doobie.h2._
import cats.effect._
// import cats._

object DummyData {
  def create[F[_]: MonadCancelThrow](xa: H2Transactor[F]): F[Unit] =
    for {
      _ <- RebuildTable.run(xa)
      _ <- CreateInitialData.run(xa)
    } yield ()
}
