package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel

/**
 *
 */
interface RoadDataSourceGateway {

	/**
	 *
	 */
	fun addRoad(roadRequestModel: RoadRequestModel): Result<RoadResponseModel>

	/**
	 *
	 */
	fun updateRoad(roadId: String, roadUpdateModel: RoadUpdateModel): Result<RoadResponseModel>

	/**
	 *
	 */
	fun removeRoad(roadId: String)

	/**
	 *
	 */
	fun getAllRoadsByNumber(roadNumber: String): List<RoadResponseModel>

	/**
	 *
	 */
	fun getRoadById(roadId: String): Result<RoadResponseModel>

	/**
	 *
	 */
	fun addJunctionToRoad(roadId: String, junctionId: String): Result<RoadResponseModel>
}
