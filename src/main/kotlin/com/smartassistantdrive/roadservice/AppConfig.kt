package com.smartassistantdrive.roadservice

import com.smartassistantdrive.roadservice.businessLayer.UseCase
import com.smartassistantdrive.roadservice.businessLayer.boundaries.RoadInputBoundary
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.DataSourceGatewayImpl
import com.smartassistantdrive.roadservice.interfaceAdaptersLayer.persistence.DrivingFlowRepository
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

	/**
	 *
	 */
	@Bean
	fun roadInput(): RoadInputBoundary {
		val roadRepository: RoadRepository = roadRepository!!
		val flowsRepository: DrivingFlowRepository = drivingFlowRepository!!

		val roadRegisterDataSourceGateway = DataSourceGatewayImpl(roadRepository, flowsRepository)
		val useCases = UseCase(roadRegisterDataSourceGateway)
		return useCases
	}
}
