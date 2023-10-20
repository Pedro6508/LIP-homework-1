// Section: 3.4.7 Drawing trees

object CoordBTree {
  case class BTree[T](value: T, left: Option[BTree[T]], right: Option[BTree[T]])
  case class Coord(value: Int, x: Double, y: Double)

  private def printTree[T](root: BTree[T], tToString: Function[T, String]): Unit =
    def next(nodeOpt: Option[BTree[T]], y: Int): Unit =
      val tab = "|\t".repeat(y)
      nodeOpt.foreach(node =>
        println(tab + tToString(node.value))
        next(node.left, y + 1)
        next(node.right, y + 1))

    next(Some(root), 0)

  private def calc(root: BTree[Int], scala: Int): BTree[Coord] =
    def next(node: BTree[Int], y: Int, preX: Int): BTree[Coord] =
      val left = node.left.map(lNode => next(lNode, y+1, 2*preX))
      val right = node.right.map(rNode => next(rNode, y+1, 2*preX+1))

      left match
        case Some(lNodeCoord) => right match
          case Some(rNodeCoord) =>
            val x = (lNodeCoord.value.x + rNodeCoord.value.x) / (2*scala)
            val coord = Coord(node.value, x*scala, y * scala)
            BTree(coord, left, right)
          case None =>
            val x = lNodeCoord.value.x / scala
            val coord = Coord(node.value, x*scala, y*scala)
            BTree(coord, left, right)
        case None =>
          val x = preX - 2^y
          val coord = Coord(node.value, x*scala, y*scala)
          BTree(coord, None, None)

    next(root, 1, 1)

  private def fill(max: Int) =
    def next(value: Int): Option[BTree[Int]] =
      lazy val left = next(2 * value)
      lazy val right = next(2 * value + 1)

      Option.unless(value > max)(BTree(value, left, right))

    next(1) getOrElse BTree(1, None, None)

  def testCase(): Unit =
    def test(max: Int, scala: Int): Unit =
      val bTree = fill(max)
      val coordBT = calc(bTree, scala)
      printTree(coordBT, (c: Coord) => s"[${c.value}](X = ${c.x}, Y = ${c.y})")
      println()

    test(43, 1)
    test(5, 2)
}
