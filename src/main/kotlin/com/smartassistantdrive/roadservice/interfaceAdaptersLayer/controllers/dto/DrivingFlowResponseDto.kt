package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

/**
 *
 */
class DrivingFlowResponseDto @JsonCreator constructor(
	/**
	 *
	 */
	@param:JsonProperty("roadId") val flowId: String,

	/**
	 *
	 */
	@param:JsonProperty("roadId") val roadId: String,

	/**
	 *
	 */
	@param:JsonProperty("roadId") val direction: Int,

	/**
	 *
	 */
	@param:JsonProperty("roadId") val numOfLanes: Int,

	/**
	 *
	 */
	@param:JsonProperty("roadId") val coordinates: ArrayList<Pair<Int, Int>>,

) : RepresentationModel<DrivingFlowResponseDto>()

/**
 *
 */
fun DrivingFlowResponseModel.toDto(link: Link): DrivingFlowResponseDto {
	return DrivingFlowResponseDto(
		flowId,
		roadId,
		idDirection,
		numOfLanes,
		roadCoordinates
	).add(
		link
	)
}
