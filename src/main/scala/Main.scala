object Main {
  import Parser._
  import CoordBTree._

  def main(args: Array[String]): Unit = {
    println("Questão 1:")
    CoordBTree.testCase()

    println("Questão 2:")
    Run.main(args)
  }
}