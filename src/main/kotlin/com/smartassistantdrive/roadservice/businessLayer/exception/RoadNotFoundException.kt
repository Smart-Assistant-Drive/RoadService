package com.smartassistantdrive.roadservice.businessLayer.exception

/**
 *
 */
class RoadNotFoundException : Exception() {
	override val message: String
		get() = "Not found any valid road with the properties specified"
}
