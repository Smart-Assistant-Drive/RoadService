package com.smartassistantdrive.roadservice.businessLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadRequestModel
import com.smartassistantdrive.roadservice.businessLayer.adapter.RoadUpdateModel
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import kotlin.test.assertEquals
import kotlin.test.fail
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
	plugin = ["pretty", "html:target/cucumber-report.html"],
	features = ["src/test/resources"]
)
class RoadCreationTest {

	private val useCase: UseCase = UseCase(DataSourceGatewayMock().getDataSourceGateway()!!)

	lateinit var firstName: String
	lateinit var firstNumber: String
	var firstCategory: Int = 0
	lateinit var secondName: String
	var secondCategory: Int = 0
	lateinit var secondNumber: String

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
