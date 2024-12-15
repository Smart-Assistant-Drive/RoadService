package com.smartassistantdrive.roadservice.businessLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.DataSourceGateway
import com.smartassistantdrive.roadservice.businessLayer.boundaries.RoadInputBoundary
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadExistsException
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

	private var logger: Logger = LoggerFactory.getLogger(UseCase::class.java)
	private val roadPolicy: RoadPolicy = RoadPolicy()

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
			return Result.failure(road.exceptionOrNull()!!)
		}
	}

	override fun changeRoad(
		roadId: Int,
		idDirection: Int,
		newNumOfLanes: Int,
		newRoadCoordinates: ArrayList<Pair<Int, Int>>,
	): Result<RoadResponseModel> {
		TODO("Not yet implemented")
	}

	override fun addJunction(
		junctionId: Int,
		outgoingRoads: ArrayList<Int>,
		junctionType: Int,
	): Result<JunctionRequestModel> {
		TODO("Not yet implemented")
	}

	override fun getRoad(roadId: String): Result<RoadResponseModel> {
		return datasourceGateway.getRoadById(roadId)
	}
}
