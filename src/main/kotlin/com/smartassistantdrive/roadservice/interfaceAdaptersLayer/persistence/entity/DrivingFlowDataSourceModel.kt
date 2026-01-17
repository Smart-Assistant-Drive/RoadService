package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity

import com.smartassistantdrive.roadservice.businessLayer.adapter.Coordinate
import org.springframework.data.annotation.Id

/**
 *
 */
class DrivingFlowDataSourceModel(
	roadId: String,
	idDirection: Int,
	numOfLanes: Int,
	roadCoordinates: ArrayList<Coordinate>,
) {

	/**
	 *
	 */
	@Id
	var id: String? = null

	/**
	 *
	 */
	var roadId: String? = roadId

	/**
	 *
	 */
	var idDirection: Int? = idDirection

	/**
	 *
	 */
	var numOfLanes: Int? = numOfLanes

	/**
	 *
	 */
	val roadCoordinates: ArrayList<Coordinate> = roadCoordinates

	constructor(
		idObj: String,
		roadId: String,
		idDirection: Int,
		numOfLanes: Int,
		roadCoordinates: ArrayList<Coordinate>,
	) : this(roadId, idDirection, numOfLanes, roadCoordinates) {
		this.id = idObj
	}

	override fun toString(): String {
		return "DrivingFlowDataSourceModel(flowId=$id, roadId=$roadId)"
	}
}
