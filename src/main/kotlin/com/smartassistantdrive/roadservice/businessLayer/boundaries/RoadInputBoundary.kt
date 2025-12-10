package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel

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
	fun addDrivingFlow(drivingFlowRequestModel: DrivingFlowRequestModel): Result<DrivingFlowResponseModel>

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
	fun addJunctionToRoad(roadId: String, junctionId: String): Result<RoadResponseModel>

	/**
	 *
	 */
	fun getJunction(junctionId: String): Result<JunctionResponseModel>

	/**
	 *
	 */
	fun updateJunction(junctionId: String, junctionUpdateModel: JunctionUpdateModel): Result<JunctionResponseModel>

	/**
	 *
	 */
	fun getRoadJunctions(roadId: String): List<JunctionResponseModel>
	fun getRoads(): List<RoadResponseModel>
}
