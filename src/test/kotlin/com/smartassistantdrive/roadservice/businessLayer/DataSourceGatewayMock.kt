package com.smartassistantdrive.roadservice.businessLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.DataSourceGateway
import com.smartassistantdrive.roadservice.domainLayer.JunctionModel
import com.smartassistantdrive.roadservice.domainLayer.JunctionType
import com.smartassistantdrive.roadservice.domainLayer.RoadModel
import com.smartassistantdrive.roadservice.domainLayer.RoadModel.Companion.copy
import com.smartassistantdrive.roadservice.domainLayer.TechnicalCategory
import com.smartassistantdrive.roadservice.domainLayer.conversion.EMPTY_ID
import com.smartassistantdrive.roadservice.domainLayer.conversion.toJunctionModel
import com.smartassistantdrive.roadservice.domainLayer.conversion.toJunctionResponse
import com.smartassistantdrive.roadservice.domainLayer.conversion.toRoadModel
import com.smartassistantdrive.roadservice.domainLayer.conversion.toRoadResponse
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class DataSourceGatewayMock {

	private val dataSourceGateway = Mockito.mock<DataSourceGateway>()
	private val db = ArrayList<RoadModel>()
	private val junctions = ArrayList<JunctionModel>()

	init {
		whenever(dataSourceGateway.addRoad(any<RoadRequestModel>())).thenAnswer {
			val roadRequestModel = it.arguments[0] as RoadRequestModel
			val road = roadRequestModel.toRoadModel()
			db.add(road)
			road.toRoadResponse()
		}
		whenever(dataSourceGateway.getRoadById(any<String>())).thenAnswer { arg ->
			val argId = arg.arguments[0] as String
			val road = db.first { it.roadId == argId }
			road.toRoadResponse()
		}
		whenever(dataSourceGateway.updateRoad(any<String>(), any<RoadUpdateModel>())).thenAnswer { arg ->
			val argId = arg.arguments[0] as String
			val newRoad = arg.arguments[1] as RoadUpdateModel
			val road = db.first { it.roadId == argId }
			db.remove(road)
			db.add(
				road.copy(
					roadName = newRoad.roadName,
					roadNumber = newRoad.roadNumber,
					category = TechnicalCategory.entries[newRoad.category]
				)
			)
		}

		whenever(dataSourceGateway.addJunction(any<JunctionRequestModel>())).thenAnswer { arg ->
			val junctionRequest = arg.arguments[0] as JunctionRequestModel
			val hashMap = HashMap<RoadModel, Int>()
			junctionRequest.outgoingRoads.forEach {
				hashMap[dataSourceGateway.getRoadById(it.idRoad).getOrNull()!!.toRoadModel()] = it.direction
			}
			val junctionModel = JunctionModel.create(
				EMPTY_ID,
				junctionType = JunctionType.entries[junctionRequest.junctionType],
				position = junctionRequest.position,
				outgoingRoads = hashMap
			)
			junctions.add(
				junctionModel
			)
			junctionModel.toJunctionResponse()
		}
		whenever(dataSourceGateway.addJunctionToRoad(any<String>(), any<String>())).thenAnswer { arg ->
			val roadId = arg.arguments[0] as String
			val junctionId = arg.arguments[1] as String
			val junction = dataSourceGateway.getJunctionById(junctionId)
			val outgoingRoads = junction.getOrNull()!!.outgoingRoads.map {
				Pair(
					dataSourceGateway.getRoadById(it.idRoad).getOrElse { error("Road not existing") }.toRoadModel(),
					it.direction
				)
			}.toCollection(ArrayList())
			db.first { it.roadId == roadId }.junctions.add(
				junction.getOrNull()!!.toJunctionModel(outgoingRoads)
			)
		}
		whenever(dataSourceGateway.getRoadJunctions(any<String>())).thenAnswer { arg ->
			val roadId = arg.arguments[0] as String
			val junctions = db.stream().filter {
				it.roadId == roadId
			}.findFirst()
			if (junctions.isPresent) {
				junctions.get().junctions
			} else {
				error("Road not existed")
			}
		}
		whenever(dataSourceGateway.getJunctionById(any<String>())).thenAnswer { arg ->
			val idRequested = arg.arguments[0] as String
			val element = junctions.stream().filter {
				it.junctionId == idRequested
			}.findFirst()
			if (element.isPresent) {
				element.get().toJunctionResponse()
			} else {
				error("Junction not found from repository")
			}
		}
	}

	fun getDataSourceGateway(): DataSourceGateway? {
		return dataSourceGateway
	}
}
