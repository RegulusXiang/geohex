package net.teralytics.terahex

import net.teralytics.terahex.geo._

case class Col(col: Long = 0) extends AnyVal {

  def +(other: Long): Col = Col(col + other)

  def -(other: Long): Col = Col(col - other)
}

case class Row(row: Long = 0) extends AnyVal {

  def +(other: Long): Row = Row(row + other)

  def -(other: Long): Row = Row(row - other)
}

case class Cell(col: Col = Col(), row: Row = Row()) {

  def moveN: Cell = Cell(col, row + 1)

  def moveS: Cell = Cell(col, row - 1)

  def moveNE: Cell = Cell(col + 1, row)

  def moveSE: Cell = Cell(col + 1, row - 1)

  def moveNW: Cell = Cell(col - 1, row + 1)

  def moveSW: Cell = Cell(col - 1, row)
}

object Cell {

  val subCenter = Cell()
  val subN = subCenter.moveN
  val subS = subCenter.moveS
  val subE = subCenter.moveNE.moveSE
  val subW = subCenter.moveNW.moveSW
  val subNE = subCenter.moveNE
  val subNW = subCenter.moveNW
  val subSE = subCenter.moveSE
  val subSW = subCenter.moveSW

  val outlierNE = subCenter.moveN.moveNE
  val outlierNW = subCenter.moveN.moveNW
  val outlierSE = subCenter.moveS.moveSE
  val outlierSW = subCenter.moveS.moveSW
}

trait Zone {

  def level: Int

  def code: String

  def location: LatLon

  def size: Double

  def geometry: Seq[LatLon]
}

trait Grid {

  def zoneByLocation(geo: LatLon, level: Int = 0): Zone

  def zoneByCode(code: String): Zone

  def encode(geo: LatLon, level: Int = 0): String = zoneByLocation(geo, level).code

  def decode(code: String): LatLon = zoneByCode(code).location
}
