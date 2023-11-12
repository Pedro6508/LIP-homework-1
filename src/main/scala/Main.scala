import cats.{Applicative, Monad}

import scala.annotation.tailrec

object Main extends App {
  sealed trait ETree[*] extends Monad[ETree] {
    override def pure[A](x: A): ETree[A] = Leaf(x)

    override def flatMap[A, B](fa: ETree[A])(f: A => ETree[B]): ETree[B] =
      fa match
        case Leaf(a) => f(a)
        case Branch(left, right) =>
          Branch(
            flatMap(left)(f),
            flatMap(right)(f)
          )

    override def tailRecM[A, B](a: A)(f: A => ETree[Either[A, B]]): ETree[B] =
      def toB(either: Either[A, B]): ETree[B] =
        either match
          case Left(x) =>
            flatMap(f(x))(toB)
          case Right(b) => Leaf(b)

      flatMap(f(a))(toB)
  }
  case class Leaf[A](value: A)
    extends ETree[A]
  case class Branch[A](left: ETree[A], right: ETree[A])
    extends ETree[A]

  def main(): Unit = {
    val mTree = Leaf[(String, Double)](("A", 0.66))
  }
}