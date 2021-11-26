package com.example.entity.student

import com.example.types.{ClassroomId, StudentId, StudentName}

final case class Student(
    id: StudentId,
    name: StudentName,
    classroomId: Option[ClassroomId],
)
