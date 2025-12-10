package com.smartassistantdrive.roadservice.businessLayer.boundaries

import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadResponseModel

/**
 *
 */
interface DataSourceGateway : RoadDataSourceGateway, DrivingFlowDataSourceGateway, JunctionDataSourceGateway {
	fun getAllRoads(): List<RoadResponseModel>
}
