package com.example.entity.classroom

import com.example.types.FacilitatorId

abstract class ClassroomRepository[F[_]]() {
  def getByAssignedFacilitator(fid: FacilitatorId): F[List[Classroom]]
}
