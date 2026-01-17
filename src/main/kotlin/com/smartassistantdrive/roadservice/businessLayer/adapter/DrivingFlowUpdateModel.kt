package com.smartassistantdrive.roadservice.businessLayer.adapter

/**
 *
 */
data class DrivingFlowUpdateModel(

	/**
	 *
	 */
	val flowId: String,

	/**
	 * Id of the direction.
	 * See [org.example.com.smartassistantdrive.roadservice.domainLayer.DrivingFlow].
	 */
	val idDirection: Int,

	/**
	 * Number of lanes of the flow of cars.
	 */
	val numOfLanes: Int,

	/**
	 * Array of coordinates of the flow.
	 */
	val roadCoordinates: ArrayList<Coordinate>,
)
