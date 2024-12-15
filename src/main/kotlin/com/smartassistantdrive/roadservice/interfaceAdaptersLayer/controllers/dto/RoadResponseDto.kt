package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

/**
 *
 */
class RoadResponseDto @JsonCreator constructor(

	/**
	 *
	 */
	@param:JsonProperty("roadId") val roadId: String,

	/**
	 *
	 */
	@param:JsonProperty("roadNumber") val roadNumber: String,

	/**
	 *
	 */
	@param:JsonProperty("roadName") val roadName: String,

	/**
	 *
	 */
	@param:JsonProperty("category") val category: String,
) : RepresentationModel<RoadResponseDto>()

/**
 *
 */
fun RoadResponseModel.toDto(link: Link): RoadResponseDto {
	return RoadResponseDto(
		roadId,
		roadNumber,
		roadName,
		category.toString()
	).add(
		link
	)
}
