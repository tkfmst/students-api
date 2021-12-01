package com.example.app.student.input

import com.example.entity.Sort

class OrderInputSpec extends munit.FunSuite {
  test("When creating an `OrderInput` from a string, it should be case-insensitive.") {
    val asc  = Right(OrderInput(Sort.Order.Asc))
    val desc = Right(OrderInput(Sort.Order.Desc))

    assertEquals(OrderInput.fromString("asc"), asc)
    assertEquals(OrderInput.fromString("ASC"), asc)
    assertEquals(OrderInput.fromString("desc"), desc)
    assertEquals(OrderInput.fromString("DESC"), desc)
  }
}
