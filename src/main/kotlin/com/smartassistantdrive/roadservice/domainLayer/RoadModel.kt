package com.smartassistantdrive.roadservice.domainLayer

/**
 *
 */
interface RoadModel {

	/**
	 * Id of the road modelled.
	 */
	val roadId: String

	/**
	 * The identification number of the road (alphanumeric value).
	 */
	val roadNumber: String

	/**
	 * Name of the road.
	 */
	val roadName: String

	/**
	 * Category of the road.
	 */
	val category: TechnicalCategory

	/**
	 * The different roadway's driving flows.
	 */
	val roadway: ArrayList<DrivingFlow>

	/**
	 * Companion object for Road construction.
	 */
	companion object {
		/**
		 * Construction method.
		 */
		fun create(
			roadId: String,
			roadNumber: String,
			roadName: String,
			category: TechnicalCategory,
			roadway: ArrayList<DrivingFlow> = ArrayList(),
		): RoadModel {
			return object : RoadModel {
				override val roadId: String
					get() = roadId
				override val roadNumber: String
					get() = roadNumber
				override val roadName: String
					get() = roadName
				override val category: TechnicalCategory
					get() = category
				override val roadway: ArrayList<DrivingFlow>
					get() = roadway

				fun addDrivingFlow(drivingFlow: DrivingFlow) {
					roadway.add(drivingFlow)
				}
			}
		}
	}
}
