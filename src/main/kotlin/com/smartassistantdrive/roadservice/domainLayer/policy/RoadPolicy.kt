package com.smartassistantdrive.roadservice.domainLayer.policy

import com.smartassistantdrive.roadservice.domainLayer.RoadModel

/**
 *
 */
class RoadPolicy {
	/**
	 *
	 */
	fun existRoad(road: RoadModel, roads: List<RoadModel>): Boolean {
		val roadNumber = road.roadNumber
		return roads.map { i -> i.roadNumber }.contains(roadNumber)
	}
}
