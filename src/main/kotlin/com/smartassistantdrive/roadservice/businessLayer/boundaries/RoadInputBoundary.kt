package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.domainLayer.RoadModel

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
	fun changeRoad(roadId: String, roadUpdateModel: RoadUpdateModel): Result<RoadResponseModel>

	/**
	 *
	 */
	fun addJunction(junctionRequestModel: JunctionRequestModel): Result<JunctionResponseModel>

	/**
	 *
	 */
	// fun updateJunction()

	/**
	 *
	 */
	fun getRoad(roadId: String): Result<RoadResponseModel>

	/**
	 *
	 */
	fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>>

	/**
	 *
	 */
	fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel>

	/**
	 *
	 */
	fun addJunctionToRoad(roadModel: RoadModel, junctionId: String): Result<RoadResponseModel>

	/**
	 *
	 */
	fun getJunction(junctionId: String): Result<JunctionResponseModel>
}
