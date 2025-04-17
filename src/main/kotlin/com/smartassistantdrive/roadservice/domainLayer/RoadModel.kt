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
	 *
	 */
	val junctionA: JunctionModel?

	/**
	 *
	 */
	val junctionB: JunctionModel?

	/**
	 *
	 */
	val junctions: ArrayList<JunctionModel>

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
			junctionA: JunctionModel? = null,
			junctionB: JunctionModel? = null,
			roadway: ArrayList<DrivingFlow> = ArrayList(),
			junctions: ArrayList<JunctionModel> = ArrayList(),
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
				override val junctionA: JunctionModel?
					get() = junctionA
				override val junctionB: JunctionModel?
					get() = junctionB
				override val junctions: ArrayList<JunctionModel>
					get() = junctions

				fun addDrivingFlow(drivingFlow: DrivingFlow) {
					roadway.add(drivingFlow)
				}

				fun addJunction(junction: JunctionModel) {
					junctions.add(junction)
				}
			}
		}

		fun RoadModel.copy(
			roadId: String = this.roadId,
			roadNumber: String = this.roadNumber,
			roadName: String = this.roadName,
			category: TechnicalCategory = this.category,
			junctionA: JunctionModel? = this.junctionA,
			junctionB: JunctionModel? = this.junctionB,
			roadway: ArrayList<DrivingFlow> = this.roadway,
			junctions: ArrayList<JunctionModel> = this.junctions,
		): RoadModel {
			return create(roadId, roadNumber, roadName, category, junctionA, junctionB, roadway, junctions)
		}
	}
}
