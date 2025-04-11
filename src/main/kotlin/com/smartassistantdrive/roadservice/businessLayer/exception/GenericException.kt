package com.smartassistantdrive.roadservice.businessLayer.exception

/**
 *
 */
class GenericException : Exception() {
	override val message: String
		get() = "Generic exception occurred"
}
