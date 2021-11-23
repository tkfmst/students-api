package com.example.app.input

import cats._
import com.example.entity.studentBelongingToFacilitator._
import com.example.app.usecase.ListByFacilitatorUsecaseInteractor
import com.example.app.input.fixture.{ListByFacilitatorInputFixture => F}
import eu.timepit.refined.auto._
import com.example.types._
import com.example.app.output.ListByFacilitatorOutput

class ListByFacilitatorInputBoundarySpec extends munit.FunSuite {

  trait Setup {
    lazy val returnGet: List[StudentBelongingToFacilitator] =
      StudentBelongingToFacilitator.create(
        sId = StudentId(1),
        sName = StudentName("a"),
        cId = Some(ClassroomId(2)),
        cName = Some(ClassroomName("b")),
      ) match {
        case Right(instance) => List(instance)
        case Left(msg)       => ???
      }

    lazy val returnCount: ListByFacilitatorCount = ListByFacilitatorCount(1)

    lazy val studentBelongingToFacilitatorRepo: StudentBelongingToFacilitatorRepository[Id] =
      new StudentBelongingToFacilitatorRepository[Id]() {
        def get(
            fid: FacilitatorId,
            page: PageLimit,
            sort: SortLogic,
            partialMatch: Option[PartialMatch],
        ): Id[List[StudentBelongingToFacilitator]] = returnGet

        def count(
            fid: FacilitatorId,
            partialMatch: Option[PartialMatch],
        ): Id[ListByFacilitatorCount] = returnCount
      }

    lazy val inputBoundary: ListByFacilitatorInputBoundary[Id] = ListByFacilitatorUsecaseInteractor[Id](
      studentBelongingToFacilitatorRepo
    )
  }

  test("`execute` can retrieve data from repository and create output") {
    new Setup {
      val expected = ListByFacilitatorOutput(
        students = returnGet,
        totalCount = returnCount,
      )

      // このinputは特に使われない
      val obtained = inputBoundary.execute(F.default)

      assertEquals(obtained, expected)
    }
  }
}
