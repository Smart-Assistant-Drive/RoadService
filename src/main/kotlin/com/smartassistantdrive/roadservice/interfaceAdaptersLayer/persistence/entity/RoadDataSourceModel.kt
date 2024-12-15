package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

/**
 *
 */
class RoadDataSourceModel(roadNumber: String, roadName: String, category: Int) {

	/**
	 *
	 */
	@Id
	var roadId: ObjectId? = null

	/**
	 *
	 */
	@Indexed(unique = true)
	var roadNumber: String? = roadNumber

	/**
	 *
	 */
	var roadName: String? = roadName

	/**
	 *
	 */
	var category: Int? = category

	constructor(
		roadId: ObjectId,
		roadNumber: String,
		roadName: String,
		category: Int,
	) : this(roadNumber, roadName, category) {
		this.roadId = roadId
	}

	override fun toString(): String {
		return "RoadDataSourceModel(roadId=$roadId, roadNumber=$roadNumber, roadName=$roadName, category=$category)"
	}
}
