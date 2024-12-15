package com.smartassistantdrive.roadservice.domainLayer

/**
 * Model of a specific junction between roads.
 */
interface JunctionModel {

	/**
	 * Id of the junction.
	 */
	val junctionId: Int

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
	 * Companion object for Junction construction.
	 */
	companion object {
		/**
		 * Construction method.
		 */
		fun create(
			junctionId: Int,
			outgoingRoads: ArrayList<RoadModel>,
			junctionType: JunctionType,
		): JunctionModel {
			return object : JunctionModel {
				override val junctionId: Int
					get() = junctionId
				override val outgoingRoads: ArrayList<RoadModel>
					get() = outgoingRoads
				override val junctionType: JunctionType
					get() = junctionType
			}
		}
	}
}
