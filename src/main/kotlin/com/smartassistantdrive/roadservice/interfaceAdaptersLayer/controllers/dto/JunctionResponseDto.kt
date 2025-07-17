package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.smartassistantdrive.roadservice.businessLayer.adapter.Coordinate
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

/**
 *
 */
class JunctionResponseDto @JsonCreator constructor(

	/**
	 *
	 */
	@param:JsonProperty("junctionId") val junctionId: String,

	/**
	 *
	 */
	@param:JsonProperty("outgoingRoads") val outgoingRoads: ArrayList<Pair<String, Int>>,

	/**
	 *
	 */
	@param:JsonProperty("junctionType") val junctionType: Int,

	/**
	 *
	 */
	@param:JsonProperty("position") val position: Pair<Coordinate, Coordinate>,
) : RepresentationModel<JunctionResponseDto>()

/**
 *
 */
fun JunctionResponseModel.toDto(link: Link): JunctionResponseDto {
	return JunctionResponseDto(
		junctionId,
		outgoingRoads,
		junctionType,
		position
	).add(link)
}
