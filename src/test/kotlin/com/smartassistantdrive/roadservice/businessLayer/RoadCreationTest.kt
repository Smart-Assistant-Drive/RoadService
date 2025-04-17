package com.smartassistantdrive.roadservice.businessLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import com.smartassistantdrive.roadservice.businessLayer.boundaries.DataSourceGateway
import com.smartassistantdrive.roadservice.domainLayer.RoadModel
import com.smartassistantdrive.roadservice.domainLayer.RoadModel.Companion.copy
import com.smartassistantdrive.roadservice.domainLayer.TechnicalCategory
import com.smartassistantdrive.roadservice.domainLayer.conversion.toRoadModel
import com.smartassistantdrive.roadservice.domainLayer.conversion.toRoadResponse
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import kotlin.test.assertEquals
import kotlin.test.fail
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(Cucumber::class)
@CucumberOptions(
	plugin = ["pretty", "html:target/cucumber-report.html"],
	features = ["src/test/resources"]
)
class RoadCreationTest {

	private var useCase: UseCase
	private val dataSourceGateway = mock<DataSourceGateway>()
	private val db = ArrayList<RoadModel>()

	lateinit var firstName: String
	lateinit var firstNumber: String
	var firstCategory: Int = 0
	lateinit var secondName: String
	var secondCategory: Int = 0
	lateinit var secondNumber: String

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

		useCase = UseCase(dataSourceGateway)
	}

	@Given("the road described as {string}, {string} and {int}")
	fun insert_name_number_category(name: String, number: String, category: Int) {
		firstName = name
		firstNumber = number
		firstCategory = category
	}

	@When("is updated with {string}, {string} and {int}")
	fun update_road(name: String, number: String, category: Int) {
		secondName = name
		secondNumber = number
		secondCategory = category
	}

	@Then("it should be updated correctly")
	fun test_update() {
		val resultRoadCreated = useCase.addRoad(RoadRequestModel(firstNumber, firstName, firstCategory))
		val road = resultRoadCreated.getOrElse { fail("Road not created") }

		useCase.changeRoad(road.roadId, RoadUpdateModel(secondNumber, secondName, secondCategory))

		val newRoad = useCase.getRoad(road.roadId)

		if (newRoad.isSuccess) {
			newRoad.getOrNull()?.let { it1 -> assertEquals(it1.roadName, "road2") }
		} else {
			fail("New road null")
		}
	}
}
