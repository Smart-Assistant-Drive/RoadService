package com.smartassistantdrive.roadservice

import com.smartassistantdrive.roadservice.businessLayer.UseCase
import com.smartassistantdrive.roadservice.businessLayer.boundaries.RoadInputBoundary
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.DataSourceGatewayImpl
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.DrivingFlowRepository
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.JunctionRepository
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.RoadRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class of the Rest Application.
 */
@Configuration
class AppConfig {

	@Autowired
	private val drivingFlowRepository: DrivingFlowRepository? = null

	@Autowired
	private val roadRepository: RoadRepository? = null

	@Autowired
	private val junctionRepository: JunctionRepository? = null

	/**
	 *
	 */
	@Bean
	fun roadInput(): RoadInputBoundary {
		if (roadRepository == null || drivingFlowRepository == null || junctionRepository == null) {
			error(IllegalStateException("One of the repositories is null"))
		}
		val roadRepository: RoadRepository = roadRepository
		val flowsRepository: DrivingFlowRepository = drivingFlowRepository
		val junctionRepository: JunctionRepository = junctionRepository

		val roadRegisterDataSourceGateway = DataSourceGatewayImpl(roadRepository, flowsRepository, junctionRepository)
		val useCases = UseCase(roadRegisterDataSourceGateway)
		return useCases
	}
}
