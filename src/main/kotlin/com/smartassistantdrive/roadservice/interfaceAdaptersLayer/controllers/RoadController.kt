package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.RoadInputBoundary
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadExistsException
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadNotFoundException
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.DrivingFlowResponseDto
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.JunctionResponseDto
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.RoadResponseDto
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.StringResponseDto
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.controllers.dto.toDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
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
import org.springframework.web.bind.annotation.PutMapping
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
		private val logger: Logger = LoggerFactory.getLogger(RoadController::class.java)
	}

	/**
	 *
	 */
	@GetMapping("/test")
	fun example(@RequestBody roadRequestModel: RoadRequestModel): HttpEntity<String> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).example(roadRequestModel)
		).withSelfRel()

		return ResponseEntity("OK", HttpStatus.CREATED)
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
				responseCode = "201",
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
				description = "Invalid flow, can't create",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid road not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error while creating",
				content = [Content()]
			)
		]
	)
	fun addFlow(@RequestBody drivingFlowRequestModel: DrivingFlowRequestModel): HttpEntity<DrivingFlowResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).addFlow(drivingFlowRequestModel)
		).withSelfRel()
		val result = roadInput.addDrivingFlow(drivingFlowRequestModel)
		return if (result.isSuccess) {
			logger.info(result.getOrNull()?.roadId)
			ResponseEntity(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			logger.error("Exception: ${result.exceptionOrNull()}")
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
				responseCode = "201",
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
			checkRoadExist(result.exceptionOrNull())
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
				description = "Invalid road, not existing",
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
			// check success of road obtained
			ResponseEntity<RoadResponseDto>(result.getOrNull()!!.toDto(links), HttpStatus.OK)
		} else {
			checkRoadNotFound(result.exceptionOrNull())
		}
	}

	/**
	 *
	 */
	@GetMapping("/roads")
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
				description = "Invalid road, not existing",
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
	fun getAllRoad(): HttpEntity<List<RoadResponseDto>> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).getAllRoad()
		).withSelfRel()

		val result = roadInput.getRoads()

		return ResponseEntity<List<RoadResponseDto>>(result.map { it.toDto(links) }.toList(), HttpStatus.OK)
	}

	/**
	 *
	 */
	@GetMapping("/flows/{id}")
	@Operation(
		summary = "Get all direction flows within an existing road",
		description = "Get existing flows of a road with a specific id",
		parameters = [
			Parameter(
				name = "id",
				description = "Road id",
				`in` = ParameterIn.PATH
			)
		],
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Flows obtained successfully",
				content = [
					Content(
						mediaType = "application/json",
						array = ArraySchema(items = Schema(implementation = DrivingFlowResponseDto::class))
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid road flow",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid road flow not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error for road flow search",
				content = [Content()]
			)
		]
	)
	fun getAllFlows(@PathVariable id: String): HttpEntity<List<DrivingFlowResponseDto>> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).getAllFlows(id)
		).withSelfRel()

		val result = roadInput.getAllDirectionFlows(id)

		return if (result.isSuccess) {
			ResponseEntity(
				result.getOrNull()!!.map {
					it.toDto(links)
				},
				HttpStatus.OK
			)
		} else {
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
	@PutMapping("/road/{id}")
	@Operation(
		summary = "Change existing road",
		description = "Change existing road with a specific id",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = RoadUpdateModel::class)
				)
			],
			required = true
		),
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
				description = "Road changed successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = RoadResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid road, cannot update a non-existing road",
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
	fun updateRoad(@PathVariable id: String, @RequestBody roadUpdateModel: RoadUpdateModel): HttpEntity<RoadResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).updateRoad(id, roadUpdateModel)
		).withSelfRel()

		val result = roadInput.changeRoad(id, roadUpdateModel)

		return if (result.isSuccess) {
			ResponseEntity<RoadResponseDto>(result.getOrNull()!!.toDto(links), HttpStatus.OK)
		} else {
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
	@PutMapping("/flows")
	@Operation(
		summary = "Change existing driving flow",
		description = "Change existing driving flow with a specific id",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = DrivingFlowUpdateModel::class)
				)
			],
			required = true
		),
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Driving flow changed successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = DrivingFlowResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid road, cannot update a non-existing driving flow",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid driving flow not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun updateDrivingFlow(
		@RequestBody drivingFlowUpdateModel: DrivingFlowUpdateModel,
	): HttpEntity<DrivingFlowResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).updateDrivingFlow(drivingFlowUpdateModel)
		).withSelfRel()

		val result = roadInput.changeDrivingFlow(drivingFlowUpdateModel)

		return if (result.isSuccess) {
			ResponseEntity<DrivingFlowResponseDto>(result.getOrNull()!!.toDto(links), HttpStatus.OK)
		} else {
			when (result.exceptionOrNull()) {
				is IllegalArgumentException -> ResponseEntity.badRequest().build()
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}

	/**
	 *
	 */
	@GetMapping("/junction/{id}")
	@Operation(
		summary = "Get existing junction",
		description = "Get existing junction with a specific id",
		parameters = [
			Parameter(
				name = "id",
				description = "Junction id to be obtained",
				`in` = ParameterIn.PATH
			)
		],
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Junction obtained successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = JunctionResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid junction",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid junction not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun getJunction(@PathVariable id: String): HttpEntity<JunctionResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).getJunction(id)
		).withSelfRel()

		val result = roadInput.getJunction(id)

		return if (result.isSuccess) {
			// check success of junction obtained
			ResponseEntity<JunctionResponseDto>(result.getOrNull()!!.toDto(links), HttpStatus.OK)
		} else {
			checkRoadNotFound(result.exceptionOrNull())
		}
	}

	/**
	 *
	 */
	@PostMapping("/junction")
	@Operation(
		summary = "Add new junction",
		description = "Add new junction with a specific id, position and outgoing roads",
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = JunctionResponseDto::class)
				)
			],
			required = true
		),
		responses = [
			ApiResponse(
				responseCode = "201",
				description = "Junction added successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = JunctionResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid junction",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "409",
				description = "Valid junction already exists",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun addJunction(@RequestBody junctionRequestModel: JunctionRequestModel): HttpEntity<JunctionResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).addJunction(junctionRequestModel)
		).withSelfRel()

		val result = roadInput.addJunction(junctionRequestModel)

		return if (result.isSuccess) {
			ResponseEntity<JunctionResponseDto>(result.getOrNull()!!.toDto(links), HttpStatus.CREATED)
		} else {
			checkRoadExist(result.exceptionOrNull())
		}
	}

	/**
	 *
	 */
	@PutMapping("/junction/{id}")
	@Operation(
		summary = "Change existing junction",
		description = "Change existing junction with a specific id",
		parameters = [
			Parameter(
				name = "id",
				description = "Junction id to change",
				`in` = ParameterIn.PATH
			)
		],
		requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = [
				Content(
					mediaType = "application/json",
					schema = Schema(implementation = JunctionUpdateModel::class)
				)
			],
			required = true
		),
		responses = [
			ApiResponse(
				responseCode = "200",
				description = "Junction changed successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = JunctionResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid junction, cannot update a non-existing junction",
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
	fun updateJunction(
		@PathVariable id: String,
		@RequestBody junctionUpdateModel: JunctionUpdateModel,
	): HttpEntity<JunctionResponseDto> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).updateJunction(id, junctionUpdateModel)
		).withSelfRel()

		val result = roadInput.updateJunction(id, junctionUpdateModel)

		return if (result.isSuccess) {
			ResponseEntity<JunctionResponseDto>(result.getOrNull()!!.toDto(links), HttpStatus.OK)
		} else {
			when (result.exceptionOrNull()) {
				is IllegalArgumentException -> ResponseEntity.badRequest().build()
				else -> ResponseEntity.internalServerError().build()
			}
		}
	}

	/**
	 *
	 */
	@GetMapping("/junction/road/{id}")
	@Operation(
		summary = "Get existing junctions on a specific road",
		description = "Get existing junction within a specific road",
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
				description = "Junctions obtained successfully",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = JunctionResponseDto::class)
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid request",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "404",
				description = "Valid road or junctions not found",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = [Content()]
			)
		]
	)
	fun getJunctionsOnRoad(@PathVariable id: String): HttpEntity<List<JunctionResponseDto>> {
		val links = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(RoadController::class.java).getJunctionsOnRoad(id)
		).withSelfRel()

		val result = roadInput.getRoadJunctions(id).map { it.toDto(links) }

		return ResponseEntity<List<JunctionResponseDto>>(result, HttpStatus.OK)
	}

	private fun <T> checkRoadExist(throwable: Throwable?): HttpEntity<T> {
		return when (throwable) {
			is IllegalArgumentException -> ResponseEntity.badRequest().build<T>()
			is RoadExistsException -> ResponseEntity.status(HttpStatus.CONFLICT).build<T>()
			else -> ResponseEntity.internalServerError().build<T>()
		}
	}

	private fun <T> checkRoadNotFound(throwable: Throwable?): HttpEntity<T> {
		return when (throwable) {
			is RoadNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
			is IllegalArgumentException -> ResponseEntity.badRequest().build()
			else -> ResponseEntity.internalServerError().build()
		}
	}
}
