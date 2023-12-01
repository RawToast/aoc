package aoc2023.day01

import scala.annotation.switch
import scala.io.Source
import scala.util.Using

trait CalibrationReader

object CalibrationReader:
  def readDigits(value: String): Int =
    val result = for {
      first  <- value.find(_.isDigit)
      last   <- value.findLast(_.isDigit)
      combine = first.toString() + last.toString()
      value  <- combine.toIntOption
    } yield value

    result.getOrElse(0)

  val valueStrings = Map(
    "one"   -> "1",
    "two"   -> "2",
    "three" -> "3",
    "four"  -> "4",
    "five"  -> "5",
    "six"   -> "6",
    "seven" -> "7",
    "eight" -> "8",
    "nine"  -> "9"
  )
  val values       = valueStrings.values.toSeq

  def readValues(value: String): Int =
    val newValue = value.foldLeft("")(
      (akk, next) =>
        valueStrings.foldLeft(akk + next)((curr, kv) => curr.replaceFirst(kv._1, kv._2))
    )
    readDigits(newValue)

  def readDigitsInFile(filename: String) =
    Using
      .resource(Source.fromResource(s"day01/$filename"))(_.getLines().toSeq)
      .foldLeft(0)((acc, line) => acc + readDigits(line))

  def readValuesInFile(filename: String) =
    Using
      .resource(Source.fromResource(s"day01/$filename"))(_.getLines().toSeq)
      .foldLeft(0)((acc, line) => acc + readValues(line))
