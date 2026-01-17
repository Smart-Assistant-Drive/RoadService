package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity

import com.smartassistantdrive.roadservice.businessLayer.adapter.Coordinate
import com.smartassistantdrive.roadservice.domainLayer.OutgoingRoad
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

/**
 *
 */
class JunctionDataSourceModel(
	outgoingRoads: ArrayList<OutgoingRoad>,
	junctionType: Int,
	position: Coordinate,
) {

	/**
	 *
	 */
	@Id
	var junctionId: ObjectId? = null

	/**
	 *
	 */
	val outgoingRoads: ArrayList<OutgoingRoad> = outgoingRoads

	/**
	 *
	 */
	var junctionType: Int = junctionType

	/**
	 *
	 */
	var position: Coordinate = position

	constructor(
		junctionId: ObjectId,
		outgoingRoads: ArrayList<OutgoingRoad>,
		junctionType: Int,
		position: Coordinate,
	) : this(outgoingRoads, junctionType, position) {
		this.junctionId = junctionId
	}

	override fun toString(): String {
		return "JunctionDataSourceModel(junctionId=$junctionId, outgoingRoads=$outgoingRoads, junctionType=$junctionType)"
	}
}
