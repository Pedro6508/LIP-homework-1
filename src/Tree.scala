case class BTree[T](value: T, left: Option[BTree[T]], right: Option[BTree[T]])

object BTree {
  def printTree[T](root: BTree[T], tToString: Function[T, String]): Unit = {
    def next(nodeOpt: Option[BTree[T]], y: Int): Unit = {
      val tab = "|\t".repeat(y)
      nodeOpt.foreach(node => {
        println(tab + tToString(node.value))
        next(node.left, y + 1)
        next(node.right, y + 1)
      })
    }

    next(Some(root), 0)
  }

  case class Coord(value: Int, x: Double, y: Double)
  def calc(root: BTree[Int], scala: Int): BTree[Coord] = {
    def next(node: BTree[Int], y: Int, preX: Int): BTree[Coord] = {
      val left = node.left.map(lNode => next(lNode, y+1, 2*preX))
      val right = node.right.map(rNode => next(rNode, y+1, 2*preX+1))

      left match {
        case Some(lNodeCoord) => right match {
          case Some(rNodeCoord) =>
            val x = (lNodeCoord.value.x + rNodeCoord.value.x) / 2
            val coord = Coord(node.value, x, y * scala)
            BTree(coord, left, right)
          case None =>
            val coord = Coord(node.value, lNodeCoord.value.x, y*scala)
            BTree(coord, left, right)
        }
        case None =>
          val x = preX - 2^y
          val coord = Coord(node.value, x, y*scala)
          BTree(coord, None, None)
      }
     }

    next(root, 0, 1)
  }

  def calcY(root: BTree[Int]) = {
    def passY(nodeOpt: Option[BTree[Int]], y: Int): Option[BTree[Int]] =
      nodeOpt match {
        case Some(node) =>
          val left = passY(node.left, y+1)
          val right = passY(node.right, y+1)
          Some(BTree(y, left, right))
        case None => None
      }

    passY(Some(root), 0) getOrElse BTree(0, None, None)
  }

  def fill(max: Int) = {
    def next(value: Int): Option[BTree[Int]] = {
      lazy val left = next(2 * value)
      lazy val right = next(2 * value + 1)

      Option.unless(value > max)(BTree(value, left, right))
    }

    next(1) getOrElse BTree(1, None, None)
  }

  def main(args: Array[String]): Unit = {
    val tree = fill(19)

    val coordTree = calc(tree, 1)
    printTree(coordTree, (x: Coord) => s"(X = ${x.x}, Y = ${x.y})")
  }
}
