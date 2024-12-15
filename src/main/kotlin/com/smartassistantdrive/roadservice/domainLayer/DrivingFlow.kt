package com.smartassistantdrive.roadservice.domainLayer

/**
 * Single riving flow of cars.
 */
interface DrivingFlow {

	/**
	 * The direction of the flow of cars.
	 * Assumed "p" as the starting point of the road, "q" the final point of the road,
	 * the value -1 means going backward from "q" to "p", value 1 viceversa,
	 * assuming to have an ordinated array of points for the road.
	 */
	val idDirection: Int

	/**
	 * Number of lanes of the flow of cars.
	 */
	val numOfLanes: Int

	/**
	 * Array of coordinates of the flow.
	 */
	val roadCoordinates: ArrayList<Pair<Int, Int>>

	/**
	 * Companion object for driving flow construction.
	 */
	companion object {
		/**
		 * Construction method.
		 */
		fun create(
			idDirection: Int,
			numOfLanes: Int,
			roadCoordinates: ArrayList<Pair<Int, Int>>,
		): DrivingFlow {
			return object : DrivingFlow {
				override val idDirection: Int
					get() = idDirection
				override val numOfLanes: Int
					get() = numOfLanes
				override val roadCoordinates: ArrayList<Pair<Int, Int>>
					get() = roadCoordinates
			}
		}
	}
}
