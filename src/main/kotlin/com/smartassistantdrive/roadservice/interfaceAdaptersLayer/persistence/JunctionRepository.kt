package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.JunctionDataSourceModel
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface JunctionRepository : MongoRepository<JunctionDataSourceModel?, String?> {
	/**
	 *
	 */
	fun findByJunctionId(id: ObjectId?): Result<JunctionDataSourceModel>
}
