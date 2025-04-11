package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowUpdateModel

/**
 *
 */
interface DrivingFlowDataSourceGateway {

	/**
	 *
	 */
	fun addDrivingFlow(drivingFlowRequestModel: DrivingFlowRequestModel): Result<String>

	/**
	 *
	 */
	fun getDrivingFlow(flowId: String): Result<DrivingFlowResponseModel>

	/**
	 *
	 */
	fun getAllDrivingFlowsByRoad(roadId: String): List<DrivingFlowResponseModel>

	/**
	 *
	 */
	fun updateDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel>
}
