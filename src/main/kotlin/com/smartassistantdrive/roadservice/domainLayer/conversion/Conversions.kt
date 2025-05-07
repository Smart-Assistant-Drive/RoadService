package com.smartassistantdrive.roadservice.domainLayer.conversion

import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.domainLayer.JunctionModel
import com.smartassistantdrive.roadservice.domainLayer.JunctionType
import com.smartassistantdrive.roadservice.domainLayer.RoadModel
import com.smartassistantdrive.roadservice.domainLayer.TechnicalCategory

/**
 * Empty id string.
 */
const val EMPTY_ID = ""

/**
 *
 */
fun RoadRequestModel.toRoadModel(): RoadModel {
	return RoadModel.create(EMPTY_ID, roadNumber, roadName, TechnicalCategory.entries[category])
}

/**
 *
 */
fun RoadResponseModel.toRoadModel(): RoadModel {
	return RoadModel.create(roadId, roadNumber, roadName, TechnicalCategory.entries[category])
}

/**
 *
 */
fun RoadModel.toRoadResponse(): RoadResponseModel {
	return RoadResponseModel(
		roadId,
		roadNumber,
		roadName,
		category.ordinal
	)
}

/**
 *
 */
fun JunctionModel.toJunctionResponse(): JunctionResponseModel {
	return JunctionResponseModel(
		junctionId = this.junctionId,
		junctionType = this.junctionType.ordinal,
		outgoingRoads = ArrayList(this.outgoingRoads.map { it.roadId }),
		position = this.position
	)
}

/**
 *
 */
fun JunctionResponseModel.toJunctionModel(outgoingRoads: ArrayList<RoadModel>): JunctionModel {
	return JunctionModel.create(
		junctionId = this.junctionId,
		junctionType = JunctionType.entries.get(this.junctionType),
		outgoingRoads = outgoingRoads,
		position = this.position
	)
}
