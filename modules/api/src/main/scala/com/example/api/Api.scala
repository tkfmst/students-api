package com.example.api

import cats.data.{Validated, ValidatedNel}
import cats.effect._
import cats.implicits._
import com.example.api.Param._
import com.example.app.student.input.{LikeWordInput, ListByFacilitatorInput}
import com.example.app.student.usecase.ListByFacilitatorUsecaseInteractor
import com.example.infra.h2.{ClassroomRepositoryOnH2, StudentRepositoryOnH2}
import com.example.interface.StudentController
import com.typesafe.scalalogging.Logger
import doobie.util.transactor.Transactor
import eu.timepit.refined.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.slf4j.LoggerFactory

class Api[F[_]](studentController: StudentController[F])(implicit F: Async[F]) extends Http4sDsl[F] {
  val logger: Logger = Logger(LoggerFactory.getLogger("name"))

  implicit private[this] class WrapperOptionValidatedParam[A, B](param: Option[ValidatedNel[A, B]]) {
    def moveOptionInside: ValidatedNel[A, Option[B]] =
      param match {
        case Some(validated) =>
          validated.fold(Validated.invalid(_), (b: B) => Validated.valid(Option(b)))
        case None            => Validated.valid(None)
      }
  }

  def routes: HttpRoutes[F] =
    HttpRoutes.of[F] {
      /**
       * GET /studens
       *
       * 教師ID(facilitator_id)を指定し教師の受け持つ生徒を返すAPIエンドポイント
       *
       * TODO このエンドポイントははREST APIとしてはfacilitatorsリソースに所属するべきかではないか?
       *   - e.g. `/facilitators/{facilitator_id}/students` など
       *
       * TODO studentがfacilitator_idを持ってないのでリソースとしては少々不自然ではある
       */
      case GET -> Root / "students"
          :? FacilitatorIdInputParam(validatedFid)
          +& PageNumberInputParam(validatedPage)
          +& LimitPerPageInputParam(validatedLimit)
          +& SortInputParam(validatedSort)
          +& OrderInputParam(validatedOrder)
          +& LikeWordStudentNameInputParam(validatedLikeWordtudentName)
          +& LikeWordClassroomNameInputParam(validatedLikeWordClassroomName) => {

        val validatedOptLikeWord: ValidatedNel[ParseFailure, Option[LikeWordInput]] =
          LikeWordInput
            .select(
              Seq(validatedLikeWordtudentName, validatedLikeWordClassroomName)
            )
            .moveOptionInside

        (
          validatedFid,
          validatedPage.moveOptionInside,
          validatedLimit.moveOptionInside,
          validatedSort.moveOptionInside,
          validatedOrder.moveOptionInside,
          validatedOptLikeWord
        )
          .mapN((fid, optPage, optLimit, optSort, optOrder, optLikeWord) => {
            val input = ListByFacilitatorInput(fid, optPage, optLimit, optSort, optOrder, optLikeWord)
            for {
              output <- studentController.listByFacilitator(input)
              res    <- Ok(output.asJson)
            } yield res
          })
          .valueOr(pfs => {
            logger.debug(
              "BadRequest:\n\t" ++ pfs.map(pf => s"${pf.sanitized}, ${pf.details}").toList.mkString("\n\t")
            )
            BadRequest()
          })
      }

      case HEAD -> Root / "hc" =>
        Ok("""{"status": OK}""".asJson)
    }
}

object Api {
  def apply[F[_]: Async](xa: Transactor[F]): Api[F] = new Api[F](
    StudentController(
      listByFacilitatorUsecase = ListByFacilitatorUsecaseInteractor(
        classroomRepo = ClassroomRepositoryOnH2(xa),
        studentRepo = StudentRepositoryOnH2(xa)
      )
    )
  )
}
