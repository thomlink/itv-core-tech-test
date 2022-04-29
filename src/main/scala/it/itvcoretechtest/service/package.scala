package it.itvcoretechtest

package service {
  trait ChecksumValidationFailure extends ThumbnailAppError
  case object InvalidChecksum extends ChecksumValidationFailure
  case class Other() extends ChecksumValidationFailure
}
