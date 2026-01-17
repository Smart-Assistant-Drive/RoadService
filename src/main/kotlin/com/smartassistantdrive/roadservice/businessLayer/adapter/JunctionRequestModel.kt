package com.smartassistantdrive.roadservice.businessLayer.adapter

import com.smartassistantdrive.roadservice.domainLayer.OutgoingRoad

/**
 *
 */
data class JunctionRequestModel(

	/**
	 * The outgoing roads of the junction: a car in a specific flow should
	 * follow the same direction flow of the outgoing road (specified with the id direction).
	 */
	val outgoingRoads: ArrayList<OutgoingRoad>,

	/**
	 *
	 */
	val junctionType: Int,

	/**
	 *
	 */
	val position: Coordinate,
)
