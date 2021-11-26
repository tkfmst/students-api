package com.example

import eu.timepit.refined.api.RefType
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString
import eu.timepit.refined.types.numeric.PosInt
import io.estatico.newtype.macros.newtype
import io.circe.{Decoder, Encoder}

package object types {

  @newtype final case class ClassroomId(toPosInt: PosInt)
  object ClassroomId {
    implicit val classroomIdEncoder: Encoder[ClassroomId] =
      Encoder.encodeInt.contramap[ClassroomId](_.toPosInt.value)
    implicit val classroomIdDecoder: Decoder[ClassroomId] = Decoder.decodeInt.emap { i =>
      RefType.applyRef[PosInt](i).map(ClassroomId(_))
    }
  }

  @JsonCodec @newtype final case class ClassroomName(toNonEmptyString: NonEmptyString)
  object ClassroomName {
    implicit val classroomNameEncoder: Encoder[ClassroomName] =
      Encoder.encodeString.contramap[ClassroomName](_.toNonEmptyString.value)
    implicit val classroomNameDecoder: Decoder[ClassroomName] = Decoder.decodeString.emap { s =>
      RefType.applyRef[NonEmptyString](s).map(ClassroomName(_))
    }
  }

  @newtype final case class FacilitatorId(toPosInt: PosInt)

  @newtype case class PageNumber(toPosInt: PosInt)
  object PageNumber {
    val default: PageNumber = PageNumber(1)
  }

  @newtype case class LimitPerPage(toPosInt: PosInt)
  object LimitPerPage {
    val default: LimitPerPage = LimitPerPage(10)
  }

  // 本来は負数は必要ないがDBから数字入れるだけでNonNegInt変換するのが冗長なので
  @newtype case class ListByFacilitatorCount(toInt: Int)
  object ListByFacilitatorCount {
    implicit val listByFacilitatorCountEncoder: Encoder[ListByFacilitatorCount] =
      Encoder.encodeInt.contramap[ListByFacilitatorCount](_.toInt)
    implicit val listByFacilitatorCountDecoder: Decoder[ListByFacilitatorCount] =
      Decoder.decodeInt.emap(i => Right(ListByFacilitatorCount(i)))
  }

  @newtype final case class StudentId(toPosInt: PosInt)
  object StudentId {
    implicit val studentIdEncoder: Encoder[StudentId] =
      Encoder.encodeInt.contramap[StudentId](_.toPosInt.value)
    implicit val studentIdDecoder: Decoder[StudentId] = Decoder.decodeInt.emap { i =>
      RefType.applyRef[PosInt](i).map(StudentId(_))
    }
  }

  @newtype final case class StudentName(toNonEmptyString: NonEmptyString)
  object StudentName {
    implicit val studentNameEncoder: Encoder[StudentName] =
      Encoder.encodeString.contramap[StudentName](_.toNonEmptyString.value)
    implicit val studentNameDecoder: Decoder[StudentName] = Decoder.decodeString.emap { s =>
      RefType.applyRef[NonEmptyString](s).map(StudentName(_))
    }
  }
}
