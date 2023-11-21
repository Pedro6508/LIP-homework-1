import cats.Monad

import scala.annotation.tailrec

object MonadTest {
  def main(args: Array[String]): Unit = {
    val monad = new Monad[Option] {
      override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)

      @tailrec
      override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] =
        f(a) match {
          case None => None
          case Some(Left(a1)) => tailRecM(a1)(f)
          case Some(Right(b)) => Some(b)
        }

      override def pure[A](x: A): Option[A] = Option(x)
    }
    def databaseOps = for {
      a <- Option(1)
      b <- Option(2)
      c <- Option(3)
      _ <- Option(println("a, b, c"))
    } yield a + b + c
    databaseOps

    val result = monad.tailRecM(0)(x => if (x < 100) Some(Left(x + 1)) else Some(Right(x)))
    println(result)
  }
}
