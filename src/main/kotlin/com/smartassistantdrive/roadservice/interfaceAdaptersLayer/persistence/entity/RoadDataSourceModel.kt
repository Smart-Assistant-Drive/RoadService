package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

/**
 *
 */
class RoadDataSourceModel(
	roadNumber: String,
	roadName: String,
	category: Int,
	junctionAid: String = "",
	junctionBid: String = "",
	junctions: ArrayList<String> = ArrayList(),
) {

	/**
	 *
	 */
	@Id
	var roadId: String? = null

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

	/**
	 *
	 */
	var junctionAid: String = junctionAid

	/**
	 *
	 */
	var junctionBid: String = junctionBid

	/**
	 *
	 */
	val junctions: ArrayList<String> = junctions

	constructor(
		roadId: String,
		roadNumber: String,
		roadName: String,
		category: Int,
		junctionAid: String = "",
		junctionBid: String = "",
		junctions: ArrayList<String> = ArrayList(),
	) : this(roadNumber, roadName, category, junctionAid, junctionBid, junctions) {
		this.roadId = roadId
	}

	override fun toString(): String {
		return "RoadDataSourceModel(roadId=$roadId, roadNumber=$roadNumber, roadName=$roadName, category=$category)"
	}
}
