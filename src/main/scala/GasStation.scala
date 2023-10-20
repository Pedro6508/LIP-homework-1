object GasStation {

  case class Station(dem: Int, limitDist: Int, pos: Double, left: Option[Station], right: Option[Station]) {
//    def apply(list: List[(Int, Double)], limit: Int): Option[Station] =
//      def rec(recList: List[Station], recLimit: Int): Option[Station] =
//        val prev = recList.headOption
//        val recTail = recList.tail
//        lazy val x: Station = Station(dem, limitDist, pos, prev, rec(List(x)++recTail, recLimit))
//        Option.unless(recTail.isEmpty)(x)

    private def next(stationOpt: Option[Station], dir: Function[Station, Option[Station]]): Option[Station] =
      stationOpt match
        case Some(st) =>
          if math.abs(pos - st.pos) > limitDist then next(dir(st), dir)
          else Some(st)
        case None => None

    private lazy val nextLeft: Option[Station] =
      def dir(st: Station) = st.nextLeft

      next(left, dir)

    private lazy val nextRight: Option[Station] =
      def dir(st: Station) = st.nextRight

      next(right, dir)

    lazy val calc: Int =
      val resLeft = left.map(st => st.calc) getOrElse 0
      val resRight = right.map(st => st.calc) getOrElse 0

      resLeft + dem + resRight
  }
}
