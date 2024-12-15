package com.smartassistantdrive.roadservice.domainLayer.conversion

import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
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
