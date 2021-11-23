package com.example.infra.h2

import cats.effect._
import doobie._
import doobie.h2._

/**
 * resource等に別書きするべきだが、今回は拡張する事もないので直書き
 */
object Conn {
  def transactor[F[_]: Async]: Resource[F, H2Transactor[F]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[F](32) // 数は適当
      xa <- H2Transactor.newH2Transactor[F](
        "jdbc:h2:mem:sample;DB_CLOSE_DELAY=-1",
        "sa", // username
        "",   // password
        ce,
      )
    } yield xa
}
