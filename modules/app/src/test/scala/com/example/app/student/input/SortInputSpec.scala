package com.example.app.student.input

import com.example.entity.Sort

class SortInputSpec extends munit.FunSuite {
  test("When creating an `SortInput` from a string, it should be case-insensitive.") {
    val id            = Right(SortInput(Sort.Key.StudentId))
    val name          = Right(SortInput(Sort.Key.StudentName))
    val classroomId   = Right(SortInput(Sort.Key.ClassroomId))
    val classroomName = Right(SortInput(Sort.Key.ClassroomName))

    assertEquals(SortInput.fromString("id"), id)
    assertEquals(SortInput.fromString("ID"), id)
    assertEquals(SortInput.fromString("name"), name)
    assertEquals(SortInput.fromString("NAME"), name)
    assertEquals(SortInput.fromString("classroom.id"), classroomId)
    assertEquals(SortInput.fromString("CLASSROOM.ID"), classroomId)
    assertEquals(SortInput.fromString("classroom.name"), classroomName)
    assertEquals(SortInput.fromString("CLASSROOM.NAME"), classroomName)
  }
}
