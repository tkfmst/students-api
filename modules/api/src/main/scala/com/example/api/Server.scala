package com.example.api

import cats.effect._
import com.example.infra.h2.Conn
import com.example.infra.h2.util.DummyData
import doobie.util.transactor.Transactor
import org.http4s.HttpApp
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._
import org.http4s.server.{Router, Server}

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
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(api(xa))
      .resource
  }
}
