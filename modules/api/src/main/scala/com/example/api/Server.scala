package com.example.api

import org.http4s.implicits._
import cats.effect._
import org.http4s.HttpApp
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import org.http4s.server.Server
import com.example.infra.h2.util.DummyData
import com.example.infra.h2.Conn
import doobie.util.transactor.Transactor

object Server extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    Conn.transactor[IO].use { xa =>
      for {
        _        <- DummyData.create(xa)
        exitCode <- resource[IO](xa).use(_ => IO.never).as(ExitCode.Success)
      } yield exitCode
    }
  }

  def api[F[_]: Async](xa: Transactor[F]): HttpApp[F] =
    Router(
      "" -> Api[F](xa).routes
    ).orNotFound

  def resource[F[_]: Async](xa: Transactor[F]): Resource[F, Server] = {
    BlazeServerBuilder[F]
      .bindHttp(8080)
      .withHttpApp(api(xa))
      .resource
  }
}
