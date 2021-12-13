package com.example.entity.classroom

import com.example.entity.ContainingWordFilter
import com.example.types.{ClassroomId, ClassroomName}
import eu.timepit.refined.auto._

class ClassroomSpec extends munit.FunSuite {
  test(
    "`CList.filterByIntermediateMatch` should be filtered out when there is a partial match keyword"
  ) {

    import Classroom.CList

    val c1 = Classroom(ClassroomId(1), ClassroomName("abcd"))
    val c2 = Classroom(ClassroomId(1), ClassroomName("defg"))
    val m1 = Some(ContainingWordFilter.ClassroomName("cd"))

    val expected = List(c1)
    val obtained = CList(List(c1, c2)).filterByIntermediateMatch(m1)

    assertEquals(obtained, expected)
  }

  test(
    "`CList.filterByIntermediateMatch` should do nothing when the `ContainingWordFilter` is `StudentName`."
  ) {

    import Classroom.CList

    val c1                  = Classroom(ClassroomId(1), ClassroomName("abcd"))
    val c2                  = Classroom(ClassroomId(1), ClassroomName("defg"))
    val filterOfStudentName = Some(ContainingWordFilter.StudentName("cd"))

    val expected = List(c1, c2)
    val obtained = CList(List(c1, c2)).filterByIntermediateMatch(filterOfStudentName)

    assertEquals(obtained, expected)
  }
}
