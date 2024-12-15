package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.RoadInputBoundary
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadExistsException
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadNotFoundException
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.RoadResponseDto
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.StringResponseDto
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.toDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 *
 */
@RestController
class RoadController(private val roadInput: RoadInputBoundary) {

	/**
	 * Companion object.
	 */
	companion object {
		private var logger: Logger = LoggerFactory.getLogger(RoadController::class.java)
	}

	/**
	 *
	 */
	@PostMapping("/drivingFlow")
	@Operation(
		summary = "Add new flow",
		description = "Add new driving flow for an existing road",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = DrivingFlowRequestModel::class)
				)
			],
			required = true
		),
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Driving flow added successfully, returns the id of the flow created",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = StringResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid flow",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid road not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun addFlow(@RequestBody drivingFlowRequestModel: DrivingFlowRequestModel): HttpEntity<StringResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).addFlow(drivingFlowRequestModel)
		).withSelfRel()
		val result = roadInput.addDrivingFlow(drivingFlowRequestModel)
		return if (result.isSuccess) {
			logger.info(result.getOrNull())
			ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			logger.error("Eccezione: ${result.exceptionOrNull()}")
			when (result.exceptionOrNull()) {
				is IllegalArgumentException -> ResponseEntity.badRequest().build()
				is RoadNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}

	/**
	 *
	 */
	@PostMapping("/road")
	@Operation(
		summary = "Add new road",
		description = "Add new road with a specific id, an alphanumeric number, a name and a category",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = RoadRequestModel::class)
				)
			],
			required = true
		),
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Road added successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = RoadResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid road",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "409",
				description = "Valid road already exists",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun addRoad(@RequestBody roadRequestModel: RoadRequestModel): HttpEntity<RoadResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).addRoad(roadRequestModel)
		).withSelfRel()

		val result = roadInput.addRoad(roadRequestModel)

		return if (result.isSuccess) {
			ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			when (result.exceptionOrNull()) {
				is IllegalArgumentException -> ResponseEntity.badRequest().build()
				is RoadExistsException -> ResponseEntity.status(HttpStatus.CONFLICT).build()
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}

	/**
	 *
	 */
	@GetMapping("/road/{id}")
	@Operation(
		summary = "Get existing road",
		description = "Get existing road with a specific id",
		parameters = [
			Parameter(
				name = "id",
				description = "Road id to be obtained",
				`in` = ParameterIn.PATH
			)
		],
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Road obtained successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = RoadResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid road",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid road not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun getRoad(@PathVariable id: String): HttpEntity<RoadResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).getRoad(id)
		).withSelfRel()

		val result = roadInput.getRoad(id)

		return if (result.isSuccess) {
			ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			when (result.exceptionOrNull()) {
				is IllegalArgumentException -> ResponseEntity.badRequest().build()
				is RoadNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}
}
