
package aoc2023.day01

import java.time.Instant
import java.util.UUID

import cats.data.EitherNec
import cats.syntax.either.*

class Day01Spec extends munit.FunSuite:

  test("can register a borrowingCard") {
    val thing   = CoolThing("thing")

    assertEquals(thing.name, "thing")
  }
