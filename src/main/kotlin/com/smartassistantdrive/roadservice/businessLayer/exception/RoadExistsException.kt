package com.smartassistantdrive.roadservice.businessLayer.exception

/**
 *
 */
class RoadExistsException : Exception() {
	override val message: String
		get() = "A valid road already exists with that id or number"
}
