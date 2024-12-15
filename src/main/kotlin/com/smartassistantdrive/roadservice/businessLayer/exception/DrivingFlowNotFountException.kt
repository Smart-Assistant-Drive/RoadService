package com.smartassistantdrive.roadservice.businessLayer.exception

/**
 *
 */
class DrivingFlowNotFountException : Exception() {
	override val message: String
		get() = "Not found any valid driving flow with the properties specified"
}
