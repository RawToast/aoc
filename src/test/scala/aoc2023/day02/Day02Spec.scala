package aoc2023.day02

import java.time.Instant
import java.util.UUID

import cats.data.EitherNec
import cats.syntax.either.*

class Day02Spec extends munit.FunSuite:

  test("can read a round") {
    val input = "3 blue, 4 red"

    val result = GameReader.parseRound(input)

    assertEquals(result, Round(blue = 3, red = 4, green = 0))
  }

  test("can parse rounds") {
    val input  = "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
    val result = GameReader.parseRounds(input)

    assertEquals(
      result,
      Some(
        Seq(
          Round(red = 4, green = 0, blue = 3),
          Round(red = 1, green = 2, blue = 6),
          Round(red = 0, green = 2, blue = 0)
        )
      )
    )
  }

  test("can read single line") {
    val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"

    val result = GameReader.readGame(input)

    assertEquals(
      result,
      Some(
        Game(
          1,
          Seq(
            Round(red = 4, green = 0, blue = 3),
            Round(red = 1, green = 2, blue = 6),
            Round(red = 0, green = 2, blue = 0)
          )
        )
      )
    )
  }

  test("can calculate the value of multiple lines") {
    val lines = Seq(
      "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
      "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
      "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
      "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
      "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )

    val result = GameReader.calculateTheValue(lines)

    assertEquals(result, 8)
  }

  test("read from file") {
    val result = GameReader.calcFromFile("input.txt")
    assertEquals(result, 2285)
  }

  test("read from file") {
    val result = GameReader.calcFromFile("input.txt")
    assertEquals(result, 2285)
  }

  test("can parse rounds") {
    val input  = Seq(
      Round(red = 4, green = 0, blue = 3),
      Round(red = 1, green = 2, blue = 6),
      Round(red = 0, green = 2, blue = 0)
    )
    val result = GameReader.calcRoundWithMinBalls(input)

    assertEquals(
      result,
      Round(red = 4, green = 2, blue = 6)
    )
  }

  test("read from file") {
    val result = GameReader.calculateRoundPowerFromFile("input.txt")
    assertEquals(result, 77021)
  }
