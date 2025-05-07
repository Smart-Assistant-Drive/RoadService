package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel

/**
 *
 */
interface JunctionDataSourceGateway {

	/**
	 *
	 */
	fun addJunction(junctionRequestModel: JunctionRequestModel): Result<JunctionResponseModel>

	/**
	 *
	 */
	fun getRoadJunctions(roadId: String): List<JunctionResponseModel>

	/**
	 *
	 */
	fun getJunctionById(junctionId: String): Result<JunctionResponseModel>
}
