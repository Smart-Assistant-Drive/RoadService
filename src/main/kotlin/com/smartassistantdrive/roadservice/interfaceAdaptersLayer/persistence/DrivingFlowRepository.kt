package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.DrivingFlowDataSourceModel
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface DrivingFlowRepository : MongoRepository<DrivingFlowDataSourceModel?, String?> {

	/**
	 *
	 */
	fun findById(id: ObjectId?): List<DrivingFlowDataSourceModel>

	/**
	 *
	 */
	fun findAllByRoadId(roadId: String?): List<DrivingFlowDataSourceModel>
}
