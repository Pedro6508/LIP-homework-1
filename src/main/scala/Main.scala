import cats.{Applicative, Eval, Group, Monad, Monoid}
import cats.syntax.all.*

import scala.annotation.tailrec
import scala.compiletime.ops.string.Length

object Main {
  trait Encode {
    def format(num: Int): String = {
      val ident = "\t".repeat(num)
      this match
        case Branch(totalFreq, left, right) =>
          (s"$ident <total: ${totalFreq.toString.format("%.3f")}>"
            + s"\n$ident ${left.format(num + 1)}"
            + s"\n$ident ${right.format(num + 1)}")
        case Letter(freq) => s"$ident <freq: ${freq.toString.format("%.3f")}>"
    }

    def solve(list: List[Double]): Encode = {
      list.sorted match
        case head :: tail =>
          tail.foldRight[Encode](this)(
            (freq, code) => code.add(Letter(freq))
          )
        case Nil => this
    }

    def sum: Double = {
      this match
      case Letter(freq) => freq
      case Branch(totalFreq, left, right) => totalFreq
    }

    def add(x: Letter): Branch = {
      this match
        case Letter(freq) =>
          Branch(
            freq + x.freq,
            Letter(math.max(x.freq, freq)),
            Letter(math.min(x.freq, freq))
          )
        case Branch(totalFreq, left, right) =>
          val total = x.freq + totalFreq
          if x.freq > right.sum
            then Branch(total, x, Branch(totalFreq, left, right))
            else Branch(total, left.add(x), right)
    }
  }
  case class Letter(freq: Double)
    extends Encode
  case class Branch(totalFreq: Double, left: Encode, right: Encode)
    extends Encode

  def main(array: Array[String]): Unit = {
    val end: Int = 100
    val list = List[Double](20, 3, 4, 50, 5, 6).map(
      i => 1f/i
    )
    println(Letter(.5).solve(list).format(0))
  }
}