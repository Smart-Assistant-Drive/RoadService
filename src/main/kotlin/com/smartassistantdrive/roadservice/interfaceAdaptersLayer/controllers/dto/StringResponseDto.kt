package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

/**
 *
 */
class StringResponseDto @JsonCreator constructor(

	/**
	 *
	 */
	@param:JsonProperty("id") val id: String,

) : RepresentationModel<StringResponseDto>()

/**
 *
 */
fun String.toDto(link: Link): StringResponseDto {
	return StringResponseDto(this).add(link)
}
