package com.example.entity.studentBelongingToFacilitator

import com.example.types.{ClassroomId, ClassroomName, StudentId, StudentName}
import eu.timepit.refined.auto._

class StudentBelongingToFacilitatorSpec extends munit.FunSuite {
  test("`create` should create an instance only when both cid & cname are exist") {
    val sId   = StudentId(1)
    val sName = StudentName("a")
    val cId   = ClassroomId(2)
    val cName = ClassroomName("b")

    assert(StudentBelongingToFacilitator.create(sId, sName, Some(cId), Some(cName)).isRight)

    assert(StudentBelongingToFacilitator.create(sId, sName, None, Some(cName)).isLeft)
    assert(StudentBelongingToFacilitator.create(sId, sName, Some(cId), None).isLeft)
    assert(StudentBelongingToFacilitator.create(sId, sName, None, None).isLeft)
  }
}
