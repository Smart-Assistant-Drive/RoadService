package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.conversion

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.DrivingFlowDataSourceModel
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.JunctionDataSourceModel
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.RoadDataSourceModel
import org.bson.types.ObjectId

/**
 *
 */
fun DrivingFlowDataSourceModel.toResponseModel(): DrivingFlowResponseModel {
	return DrivingFlowResponseModel(
		id.toString(),
		roadId!!,
		idDirection!!,
		numOfLanes!!,
		roadCoordinates
	)
}

/**
 *
 */
fun RoadDataSourceModel.toResponseModel(): RoadResponseModel {
	return RoadResponseModel(
		roadId.toString(),
		roadNumber!!,
		roadName!!,
		category!!
	)
}

/**
 *
 */
fun RoadRequestModel.toDataSourceModel(): RoadDataSourceModel {
	return RoadDataSourceModel(roadNumber, roadName, category)
}

/**
 *
 */
fun DrivingFlowResponseModel.toDataSourceModel(): DrivingFlowDataSourceModel {
	val existingFlowId = flowId
	val roadToUpdate = DrivingFlowDataSourceModel(roadId, idDirection, numOfLanes, roadCoordinates)
	roadToUpdate.id = existingFlowId
	return roadToUpdate
}

/**
 *
 */
fun JunctionRequestModel.toDataSourceModel(): JunctionDataSourceModel {
	return JunctionDataSourceModel(outgoingRoads, junctionType, position)
}

/**
 *
 */
fun JunctionDataSourceModel.toResponseModel(): JunctionResponseModel {
	return JunctionResponseModel(
		junctionId.toString(),
		outgoingRoads,
		junctionType,
		position
	)
}

/**
 *
 */
fun JunctionResponseModel.toDataSourceModel(): JunctionDataSourceModel {
	return JunctionDataSourceModel(
		ObjectId(junctionId),
		outgoingRoads,
		junctionType,
		position
	)
}
