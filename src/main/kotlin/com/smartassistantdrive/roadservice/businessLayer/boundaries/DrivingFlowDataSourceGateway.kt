package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel

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
}