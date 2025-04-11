package com.smartassistantdrive.roadservice.businessLayer.adapter

/**
 *
 */
data class JunctionUpdateModel(

	/**
	 * The outgoing roads of the junction: a car in a specific flow should
	 * follow the same direction flow of the outgoing road (specified with the id direction).
	 */
	val newOutgoingRoads: ArrayList<String>,

	/**
	 *
	 */
	val newJunctionType: Int,
)
