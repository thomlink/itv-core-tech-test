package it.itvcoretechtest

package object service {
  sealed trait ChecksumValidationResult
  case object Success extends ChecksumValidationResult
  case class Failure(reason: String) extends ChecksumValidationResult
}
