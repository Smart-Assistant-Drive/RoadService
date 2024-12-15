package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel

/**
 *
 */
interface RoadInputBoundary {

	/**
	 *
	 */
	fun addRoad(roadRequestModel: RoadRequestModel): Result<RoadResponseModel>

	/**
	 *
	 */
	fun addDrivingFlow(drivingFlowRequestModel: DrivingFlowRequestModel): Result<String>

	/**
	 *
	 */
	fun changeRoad(
		roadId: Int,
		idDirection: Int,
		newNumOfLanes: Int,
		newRoadCoordinates: ArrayList<Pair<Int, Int>>,
	): Result<RoadResponseModel>

	/**
	 *
	 */
	fun addJunction(
		junctionId: Int,
		outgoingRoads: ArrayList<Int>,
		junctionType: Int,
	): Result<JunctionRequestModel>

	/**
	 *
	 */
	fun getRoad(roadId: String): Result<RoadResponseModel>
}
