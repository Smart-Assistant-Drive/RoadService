package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.RoadDataSourceModel
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface RoadRepository : MongoRepository<RoadDataSourceModel?, String?> {

	/**
	 *
	 */
	fun findByRoadId(roadId: ObjectId?): List<RoadDataSourceModel>

	/**
	 *
	 */
	fun findByRoadNumber(roadNumber: String?): List<RoadDataSourceModel>

	/**
	 *
	 */
	fun removeByRoadId(roadId: ObjectId?)
}
