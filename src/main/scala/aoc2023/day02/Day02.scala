// Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
//  12 red cubes, 13 green cubes, and 14 blue cubes?
package aoc2023.day02

import cats.implicits.*
import cats.instances.int
import cats.kernel.Semigroup
import cats.syntax.*
import scala.collection.immutable.Seq
import scala.io.Source
import scala.util.Using

case class Game(id: Int, rounds: Seq[Round])

case class Round(red: Int, green: Int, blue: Int):
  def power() = red * green * blue

object Round:
  given roundSemigroup: Semigroup[Round] = new Semigroup[Round] {
    def combine(a: Round, b: Round) = Round(
      red = a.red + b.red,
      green = a.green + b.green,
      blue = a.blue + b.blue
    )
  }

object GameReader {

  def parseGameInfo(gameInfo: String) = gameInfo.split(" ") match
    case Array("Game", id) => id.toIntOption.map(i => Game(i, Seq.empty))
    case _                 => None

  def parseRound(roundString: String): Round =
    val singleBallRounds = for {
      ballAndCounts  <- roundString.split(", ")
      singleBallCount = ballAndCounts.split(" ") match
                          case Array(x, "blue")  =>
                            x.toIntOption.map(v => Round(blue = v, green = 0, red = 0))
                          case Array(x, "green") =>
                            x.toIntOption.map(v => Round(blue = 0, green = v, red = 0))
                          case Array(x, "red")   =>
                            x.toIntOption.map(v => Round(blue = 0, green = 0, red = v))
                          case _                 => None
    } yield singleBallCount

    singleBallRounds.flatten.foldLeft(Round(0, 0, 0))(_ |+| _)

  def parseRounds(roundString: String) =
    val rounds = for {
      roundStr <- roundString.split("; ")
      round     = parseRound(roundStr)
    } yield round

    rounds.foldLeft[Option[Seq[Round]]](Some(Seq.empty)) {
      (acc, round) =>
        (acc, round) match {
          case (Some(rs), Round(red, _, _)) if red > 12     => None
          case (Some(rs), Round(_, green, _)) if green > 13 => None
          case (Some(rs), Round(_, _, blue)) if blue > 14   => None
          case (Some(rs), r: Round)                         => Some(rs :+ r)
          case (None, _)                                    => None
        }
    }

  def parseRoundsNoLimit(roundString: String) =
    val rounds = for {
      roundStr <- roundString.split("; ")
      round     = parseRound(roundStr)
    } yield round

    rounds.foldLeft(Seq.empty[Round])((acc, round) => acc :+ round)

  def readGameData(gameInfo: String, rounds: String) =
    for {
      game   <- parseGameInfo(gameInfo)
      rounds <- parseRounds(rounds)
    } yield game.copy(rounds = rounds)

  def readGame(game: String): Option[Game] =
    game.split(": ") match
      case Array(gameInfo, rounds) =>
        readGameData(gameInfo, rounds)
      case _                       => None

  def readValidGameId(game: String): Int =
    readGame(game).fold(0)(_.id)

  def calculateTheValue(games: Seq[String]) =
    games
      .map(readValidGameId(_))
      .foldLeft(0)((acc, game) => acc + game)

  def calcFromFile(filename: String) =
    Using
      .resource(Source.fromResource(s"day02/$filename"))(_.getLines().toSeq)
      .foldLeft(0)((acc, line) => acc + readValidGameId(line))

  def calcRoundWithMinBalls(rounds: Seq[Round]) =
    rounds.foldLeft(Round(0, 0, 0))(
      (acc, r) =>
        Round(
          red = Math.max(acc.red, r.red),
          green = Math.max(acc.green, r.green),
          blue = Math.max(acc.blue, r.blue)
        )
    )

  def readGameDataQ2(gameInfo: String, roundStr: String) =
    for {
      game  <- parseGameInfo(gameInfo)
      rounds = parseRoundsNoLimit(roundStr)
    } yield game.copy(rounds = rounds)

  def readGameQ2(game: String): Option[Game] =
    game.split(": ") match
      case Array(gameInfo, rounds) =>
        readGameDataQ2(gameInfo, rounds)
      case _                       => None

  def calculateRoundPowerFromFile(filename: String) =
    Using
      .resource(Source.fromResource(s"day02/$filename"))(_.getLines().toSeq)
      .foldLeft(0)(
        (acc, line) => acc + readGameQ2(line).fold(0)(g => calcRoundWithMinBalls(g.rounds).power())
      )

}
