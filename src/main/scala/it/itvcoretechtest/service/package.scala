package it.itvcoretechtest

package object service {
  trait ChecksumValidationFailure extends ThumbnailAppError
  case object InvalidChecksum extends ChecksumValidationFailure
  case class Other() extends ChecksumValidationFailure
}
