package it.itvcoretechtest

import it.itvcoretechtest.service.ChecksumValidationFailure

package object checksum {
  case class CalculatedChecksum()

  sealed trait ChecksumCalculationError extends ChecksumValidationFailure
  case class SomeError(desc: String) extends ChecksumCalculationError
}
