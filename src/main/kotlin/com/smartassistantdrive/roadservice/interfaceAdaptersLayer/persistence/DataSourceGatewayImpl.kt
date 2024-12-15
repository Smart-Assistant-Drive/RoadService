package com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence

import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.DrivingFlowResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.DataSourceGateway
import com.smartassistantdrive.roadservice.businessLayer.exception.RoadNotFoundException
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.DrivingFlowDataSourceModel
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.entity.RoadDataSourceModel
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
) : DataSourceGateway {

	private var logger: Logger = LoggerFactory.getLogger(DataSourceGatewayImpl::class.java)

	override fun addRoad(roadRequestModel: RoadRequestModel): Result<RoadResponseModel> {
		val roadDataSourceModel =
			RoadDataSourceModel(roadRequestModel.roadNumber, roadRequestModel.roadName, roadRequestModel.category)
		val result = roadRepository.save(roadDataSourceModel)
		val id = result.roadId.toString()
		logger.info("Saved new road $id")
		return Result.success(RoadResponseModel(id, result.roadNumber!!, result.roadName!!, result.category!!))
	}

	override fun updateRoad(roadId: String, roadUpdateModel: RoadUpdateModel): Result<RoadResponseModel> {
		TODO("Not yet implemented")
	}

	override fun removeRoad(roadId: String) {
		TODO("Not yet implemented")
	}

	override fun getAllRoadsByNumber(roadNumber: String): List<RoadResponseModel> {
		val result = roadRepository.findByRoadNumber(roadNumber)
		return result.stream().map { i ->
			RoadResponseModel(i.roadId.toString(), i.roadNumber!!, i.roadName!!, i.category!!)
		}.collect(Collectors.toList())
	}

	override fun getRoadById(roadId: String): Result<RoadResponseModel> {
		val list = roadRepository.findByRoadId(ObjectId(roadId))
		list.stream().map { i ->
			RoadResponseModel(i.roadId.toString(), i.roadNumber!!, i.roadName!!, i.category!!)
		}.findFirst().getOrNull()?.let {
			return Result.success(it)
		}
		return Result.failure(RoadNotFoundException())
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
			DrivingFlowResponseModel(
				it.id.toString(),
				it.roadId!!,
				it.idDirection!!,
				it.numOfLanes!!,
				it.roadCoordinates!!
			)
		}.findFirst().getOrNull()?.let {
			return Result.success(it)
		}
		return Result.failure(RoadNotFoundException())
	}
}
