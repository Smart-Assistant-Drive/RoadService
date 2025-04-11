package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.JunctionDataSourceModel
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface JunctionRepository : MongoRepository<JunctionDataSourceModel?, String?>
