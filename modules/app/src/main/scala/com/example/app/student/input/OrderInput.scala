package com.example.app.student.input

import com.example.entity.Sort

final case class OrderInput(toSortOrder: Sort.Order) {}
object OrderInput                                    {
  def fromString(value: String): Either[String, OrderInput] =
    value.toLowerCase match {
      case "asc"  => Right(OrderInput(Sort.Order.Asc))
      case "desc" => Right(OrderInput(Sort.Order.Desc))
      case _      => Left(s"OrderInput.fromString is Error: value=${value}")
    }

  val default: OrderInput = OrderInput(Sort.Order.Asc)
}
