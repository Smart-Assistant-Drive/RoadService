package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.RoadDataSourceModel
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface RoadRepository : MongoRepository<RoadDataSourceModel?, String?> {

	/**
	 *
	 */
	override fun findAll(): List<RoadDataSourceModel>

	/**
	 *
	 */
	fun findByRoadId(roadId: String): List<RoadDataSourceModel>

	/**
	 *
	 */
	fun findByRoadNumber(roadNumber: String?): List<RoadDataSourceModel>

	/**
	 *
	 */
	fun removeByRoadId(roadId: String?)
}
