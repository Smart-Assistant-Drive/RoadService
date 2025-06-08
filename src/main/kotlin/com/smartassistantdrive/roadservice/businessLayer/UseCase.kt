package com.smartassistantdrive.roadservice.businessLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.DataSourceGateway
import com.smartassistantdrive.roadservice.businessLayer.boundaries.RoadInputBoundary
import com.smartassistantdrive.roadservice.businessLayer.exception.GenericException
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadExistsException
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadNotFoundException
import com.smartassistantdrive.roadservice.domainLayer.RoadModel
import com.smartassistantdrive.roadservice.domainLayer.conversion.toRoadModel
import com.smartassistantdrive.roadservice.domainLayer.policy.RoadPolicy
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class UseCase(
	private var datasourceGateway: DataSourceGateway,
) : RoadInputBoundary {

	private val logger: Logger = LoggerFactory.getLogger(UseCase::class.java)
	private val roadPolicy: RoadPolicy = RoadPolicy()

	// TODO implement cashing
	// private val roadCash: HashSet<RoadModel> = HashSet()
	// private val drivingFlowsCash: HashSet<DrivingFlow> = HashSet()
	// private val junctionsCash: HashSet<JunctionModel> = HashSet()

	override fun addRoad(roadRequestModel: RoadRequestModel): Result<RoadResponseModel> {
		val roadsList = datasourceGateway.getAllRoadsByNumber(roadRequestModel.roadNumber)
		if (roadPolicy.existRoad(roadRequestModel.toRoadModel(), roadsList.map { i -> i.toRoadModel() })) {
			return Result.failure(RoadExistsException())
		}
		return datasourceGateway.addRoad(roadRequestModel)
	}

	override fun addDrivingFlow(drivingFlowRequestModel: DrivingFlowRequestModel): Result<String> {
		logger.info("Added new driving flow")
		val road = datasourceGateway.getRoadById(drivingFlowRequestModel.roadId)
		if (road.isSuccess) {
			return datasourceGateway.addDrivingFlow(drivingFlowRequestModel)
		} else {
			return when (road.exceptionOrNull()) {
				is Exception -> Result.failure<String>(road.exceptionOrNull() as Exception)
				else -> Result.failure<String>(GenericException())
			}
		}
	}

	override fun changeRoad(roadId: String, roadUpdateModel: RoadUpdateModel): Result<RoadResponseModel> {
		logger.info("Updating existing road...")
		val road = datasourceGateway.getRoadById(roadId)
		if (road.isSuccess) {
			return datasourceGateway.updateRoad(roadId, roadUpdateModel)
		} else {
			return road
		}
	}

	override fun addJunction(junctionRequestModel: JunctionRequestModel): Result<JunctionResponseModel> {
		val junctionResponseModel = datasourceGateway.addJunction(junctionRequestModel).getOrNull()

		return when {
			junctionResponseModel != null -> {
				junctionResponseModel.outgoingRoads.forEach {
					val road = getRoad(it.first)
					if (road.isSuccess) {
						addJunctionToRoad(road.getOrNull()!!.toRoadModel(), junctionResponseModel.junctionId)
					} else {
						return Result.failure(RoadNotFoundException())
					}
				}
				Result.success(junctionResponseModel)
			}
			else -> Result.failure(GenericException())
		}
	}

	override fun getRoad(roadId: String): Result<RoadResponseModel> {
		return datasourceGateway.getRoadById(roadId)
	}

	override fun getAllDirectionFlows(roadId: String): Result<List<DrivingFlowResponseModel>> {
		val road = datasourceGateway.getRoadById(roadId)
		if (road.isFailure) {
			return Result.failure(RoadNotFoundException())
		}
		return Result.success(datasourceGateway.getAllDrivingFlowsByRoad(roadId))
	}

	override fun changeDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel> {
		return datasourceGateway.updateDrivingFlow(drivingFlowUpdateModel)
	}

	override fun addJunctionToRoad(roadModel: RoadModel, junctionId: String): Result<RoadResponseModel> {
		logger.info("Adding junction to existing road...")
		val result = datasourceGateway.addJunctionToRoad(roadModel.roadId, junctionId)
		return result
	}

	override fun getJunction(junctionId: String): Result<JunctionResponseModel> {
		val result = datasourceGateway.getJunctionById(junctionId)
		return result
	}
}
