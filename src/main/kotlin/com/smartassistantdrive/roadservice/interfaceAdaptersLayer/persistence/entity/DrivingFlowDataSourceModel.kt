package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

/**
 *
 */
class DrivingFlowDataSourceModel(
	roadId: String,
	idDirection: Int,
	numOfLanes: Int,
	roadCoordinates: ArrayList<Pair<Int, Int>>,
) {

	/**
	 *
	 */
	@Id
	var id: ObjectId? = null

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
	val roadCoordinates: ArrayList<Pair<Int, Int>>? = roadCoordinates

	constructor(
		idObj: ObjectId,
		roadId: String,
		idDirection: Int,
		numOfLanes: Int,
		roadCoordinates: ArrayList<Pair<Int, Int>>,
	) : this(roadId, idDirection, numOfLanes, roadCoordinates) {
		this.id = idObj
	}

	override fun toString(): String {
		return "DrivingFlowDataSourceModel(flowId=$id, roadId=$roadId)"
	}
}
