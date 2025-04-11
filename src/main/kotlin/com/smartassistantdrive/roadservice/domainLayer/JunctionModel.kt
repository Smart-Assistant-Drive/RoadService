package com.smartassistantdrive.roadservice.domainLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.Coordinate

/**
 * Model of a specific junction between roads.
 */
interface JunctionModel {

	/**
	 * Id of the junction.
	 */
	val junctionId: String

	/**
	 * The outgoing roads of the junction: a car in a specific flow should
	 * follow the same direction flow of the outgoing road (specified with the id direction).
	 */
	val outgoingRoads: ArrayList<RoadModel>

	/**
	 *
	 */
	val junctionType: JunctionType

	/**
	 *
	 */
	val position: Pair<Coordinate, Coordinate>

	/**
	 * Companion object for Junction construction.
	 */
	companion object {
		/**
		 * Construction method.
		 */
		fun create(
			junctionId: String,
			outgoingRoads: ArrayList<RoadModel>,
			junctionType: JunctionType,
			position: Pair<Coordinate, Coordinate>,
		): JunctionModel {
			return object : JunctionModel {
				override val junctionId: String
					get() = junctionId
				override val outgoingRoads: ArrayList<RoadModel>
					get() = outgoingRoads
				override val junctionType: JunctionType
					get() = junctionType
				override val position: Pair<Coordinate, Coordinate>
					get() = position
			}
		}
	}
}
