package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity

import com.smartassistantdrive.roadservice.businessLayer.adapter.Coordinate
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

/**
 *
 */
class JunctionDataSourceModel(
	outgoingRoads: ArrayList<Pair<String, Int>>,
	junctionType: Int,
	position: Pair<Coordinate, Coordinate>,
) {

	/**
	 *
	 */
	@Id
	var junctionId: ObjectId? = null

	/**
	 *
	 */
	val outgoingRoads: ArrayList<Pair<String, Int>> = outgoingRoads

	/**
	 *
	 */
	var junctionType: Int = junctionType

	/**
	 *
	 */
	var position: Pair<Coordinate, Coordinate> = position

	constructor(
		junctionId: ObjectId,
		outgoingRoads: ArrayList<Pair<String, Int>>,
		junctionType: Int,
		position: Pair<Coordinate, Coordinate>,
	) : this(outgoingRoads, junctionType, position) {
		this.junctionId = junctionId
	}

	override fun toString(): String {
		return "JunctionDataSourceModel(junctionId=$junctionId, outgoingRoads=$outgoingRoads, junctionType=$junctionType)"
	}
}
