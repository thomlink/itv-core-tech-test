package it.itvcoretechtest

package object checksum {
  case class CalculatedChecksum()

  sealed trait ChecksumCalculationError
  case class SomeError(desc: String) extends ChecksumCalculationError
}
