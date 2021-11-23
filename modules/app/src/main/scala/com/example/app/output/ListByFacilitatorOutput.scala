package com.example.app.output

import com.example.types.ListByFacilitatorCount
import com.example.entity.studentBelongingToFacilitator.StudentBelongingToFacilitator
import io.circe.generic.JsonCodec

@JsonCodec final case class ListByFacilitatorOutput(
    students: Seq[StudentBelongingToFacilitator],
    totalCount: ListByFacilitatorCount
)
