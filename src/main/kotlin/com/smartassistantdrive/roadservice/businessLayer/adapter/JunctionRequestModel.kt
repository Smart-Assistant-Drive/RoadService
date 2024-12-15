package com.smartassistantdrive.roadservice.businessLayer.adapter

/**
 *
 */
data class JunctionRequestModel(
	/**
	 * Id of the junction.
	 */
	val junctionId: Int,

	/**
	 * The outgoing roads of the junction: a car in a specific flow should
	 * follow the same direction flow of the outgoing road (specified with the id direction).
	 * TODO valutare l'utilizzo di concetti di dominio
	 */
	val outgoingRoads: ArrayList<String>,

	/**
	 *
	 */
	val junctionType: Int,
)
