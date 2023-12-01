package aoc2023.day01

import java.time.Instant
import java.util.UUID

import cats.data.EitherNec
import cats.syntax.either.*

class Day01Spec extends munit.FunSuite:
  test("can read single input") {
    val input = "pqr3stu8vwx"

    val result = CalibrationReader.readDigits(input)

    assertEquals(result, 38)
  }

  test("example input adds to 142") {
    val inputs = Seq("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet")
    val result = inputs
      .map(CalibrationReader.readDigits)
      .sum

    assertEquals(result, 142)
  }

  test("can read sample input") {
    val result = CalibrationReader.readDigitsInFile("simple.txt")
    assertEquals(result, 100)
  }

  test("read input adds to some big number") {
    val result = CalibrationReader.readDigitsInFile("input.txt")
    assertEquals(result, 53334)
  }

  test("can read input with words for numbers") {
    val input = "two1nine"
    val result = CalibrationReader.readValues(input)
    assertEquals(result, 29)
    
    val input2 = "eightwothree"
    val result2 = CalibrationReader.readValues(input2)
    assertEquals(result2, 83)

    val input3 = "abcone2threexyz"
    val result3 = CalibrationReader.readValues(input3)
    assertEquals(result3, 13)
  }

  test("example input adds to 281") {
    val inputs = Seq(
      "two1nine",
      "eightwothree",
      "abcone2threexyz",
      "xtwone3four",
      "4nineeightseven2",
      "zoneight234",
      "7pqrstsixteen"
    )
    val result = inputs
      .map(CalibrationReader.readValues)
      .sum

    assertEquals(result, 281)
  }

  test("read input values adds to some big number") {
    val result = CalibrationReader.readValuesInFile("input.txt")
    assertEquals(result, 52834)
  }
