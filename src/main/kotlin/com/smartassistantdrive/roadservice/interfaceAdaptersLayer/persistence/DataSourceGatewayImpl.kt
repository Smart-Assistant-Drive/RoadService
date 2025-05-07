package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.DataSourceGateway
import com.smartassistantdrive.roadservice.businessLayer.exception.DrivingFlowNotFountException
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadNotFoundException
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.conversion.toDataSourceModel
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.conversion.toResponseModel
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.DrivingFlowDataSourceModel
import java.util.stream.Collectors
import kotlin.jvm.optionals.getOrNull
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
@Suppress("TooGenericExceptionCaught")
class DataSourceGatewayImpl(
	private val roadRepository: RoadRepository,
	private val drivingFlowRepository: DrivingFlowRepository,
	private val junctionRepository: JunctionRepository,
) : DataSourceGateway {

	private val logger: Logger = LoggerFactory.getLogger(DataSourceGatewayImpl::class.java)

	override fun addRoad(roadRequestModel: RoadRequestModel): Result<RoadResponseModel> {
		val roadDataSourceModel = roadRequestModel.toDataSourceModel()
		val result = roadRepository.save(roadDataSourceModel)
		val id = result.roadId.toString()
		logger.info("Saved new road $id")
		return Result.success(result.toResponseModel())
	}

	override fun updateRoad(roadId: String, roadUpdateModel: RoadUpdateModel): Result<RoadResponseModel> {
		val roadToUpdate = roadRepository.findByRoadId(ObjectId(roadId)).first()
		roadToUpdate.roadName = roadUpdateModel.roadName
		roadToUpdate.roadNumber = roadUpdateModel.roadNumber
		roadToUpdate.category = roadUpdateModel.category
		val result = roadRepository.save(roadToUpdate)
		return Result.success(result.toResponseModel())
	}

	override fun removeRoad(roadId: String) {
		roadRepository.removeByRoadId(ObjectId(roadId))
	}

	override fun getAllRoadsByNumber(roadNumber: String): List<RoadResponseModel> {
		val result = roadRepository.findByRoadNumber(roadNumber)
		return result.stream().map {
			it.toResponseModel()
		}.collect(Collectors.toList())
	}

	override fun getRoadById(roadId: String): Result<RoadResponseModel> {
		val list = roadRepository.findByRoadId(ObjectId(roadId))
		list.stream().map {
			it.toResponseModel()
		}.findFirst().getOrNull()?.let {
			return Result.success(it)
		}
		return Result.failure(RoadNotFoundException())
	}

	override fun addJunctionToRoad(roadId: String, junctionId: String): Result<RoadResponseModel> {
		val roads = roadRepository.findByRoadId(ObjectId(roadId))
		if (roads.isNotEmpty()) {
			val road = roads.first()
			road.junctions.addLast(junctionId)
			roadRepository.save(road)
			return Result.success(road.toResponseModel())
		} else {
			return Result.failure(RoadNotFoundException())
		}
	}

	override fun addDrivingFlow(drivingFlowRequestModel: DrivingFlowRequestModel): Result<String> {
		try {
			val result = getRoadById(drivingFlowRequestModel.roadId)
			if (result.isSuccess) {
				val flowToSave = DrivingFlowDataSourceModel(
					drivingFlowRequestModel.roadId,
					1,
					2,
					drivingFlowRequestModel.roadCoordinates
				)
				drivingFlowRepository.insert(flowToSave)
				return Result.success(flowToSave.id.toString())
			}
			throw RoadNotFoundException()
		} catch (e: Exception) {
			return Result.failure(e)
		}
	}

	override fun getDrivingFlow(flowId: String): Result<DrivingFlowResponseModel> {
		val list = drivingFlowRepository.findById(ObjectId(flowId))
		list.stream().map {
			it.toResponseModel()
		}.findFirst().getOrNull()?.let {
			return Result.success(it)
		}
		return Result.failure(RoadNotFoundException())
	}

	override fun getAllDrivingFlowsByRoad(roadId: String): List<DrivingFlowResponseModel> {
		return drivingFlowRepository.findAllByRoadId(roadId).map {
			it.toResponseModel()
		}
	}

	override fun updateDrivingFlow(drivingFlowUpdateModel: DrivingFlowUpdateModel): Result<DrivingFlowResponseModel> {
		val result = getDrivingFlow(drivingFlowUpdateModel.flowId)
		if (result.isSuccess) {
			val newFlow = drivingFlowRepository.save(result.getOrNull()!!.toDataSourceModel())
			return Result.success(newFlow.toResponseModel())
		} else {
			return Result.failure(DrivingFlowNotFountException())
		}
	}

	override fun addJunction(junctionRequestModel: JunctionRequestModel): Result<JunctionResponseModel> {
		val junction = junctionRepository.save(junctionRequestModel.toDataSourceModel())
		return Result.success(junction.toResponseModel())
	}

	override fun getRoadJunctions(roadId: String): List<JunctionResponseModel> {
		return roadRepository.findByRoadId(ObjectId(roadId)).stream().findFirst().get().junctions.map {
			junctionRepository.findByJunctionId(ObjectId(it)).getOrNull()!!.toResponseModel()
		}
	}

	override fun getJunctionById(junctionId: String): Result<JunctionResponseModel> {
		val result = junctionRepository.findByJunctionId(ObjectId(junctionId))
		return if (result.isSuccess) {
			Result.success(result.getOrNull()!!.toResponseModel())
		} else {
			Result.failure<JunctionResponseModel>(Exception("Junction not found"))
		}
	}
}
