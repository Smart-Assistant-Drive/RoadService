package com.smartassistantdrive.roadservice.businessLayer.adapter

/**
 *
 */
data class JunctionResponseModel(

	/**
	 * Id of the junction.
	 */
	val junctionId: String,

	/**
	 * The outgoing roads of the junction: a car in a specific flow should
	 * follow the same direction flow of the outgoing road (specified with the id direction).
	 */
	val outgoingRoads: ArrayList<Pair<String, Int>>,

	/**
	 *
	 */
	val junctionType: Int,

	/**
	 *
	 */
	val position: Pair<Coordinate, Coordinate>,
)
