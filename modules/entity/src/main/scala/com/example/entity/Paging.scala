package com.example.entity

import com.example.types.{LimitPerPage, PageNumber}

final case class Paging(page: PageNumber, limit: LimitPerPage, sort: Sort)

final case class Sort(key: Sort.Key, order: Sort.Order)
object Sort {
  sealed abstract class Key() extends Product with Serializable
  object Key {
    case object StudentId     extends Key
    case object StudentName   extends Key
    case object ClassroomId   extends Key
    case object ClassroomName extends Key
  }

  sealed abstract class Order() extends Product with Serializable
  object Order {
    case object Asc  extends Order
    case object Desc extends Order
  }
}
