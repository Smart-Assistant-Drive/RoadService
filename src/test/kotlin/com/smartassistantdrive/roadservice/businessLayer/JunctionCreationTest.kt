package com.smartassistantdrive.roadservice.businessLayer

import com.smartassistantdrive.roadservice.businessLayer.adapter.Coordinate
import com.smartassistantdrive.roadservice.businessLayer.adapter.JunctionRequestModel
import com.smartassistantdrive.roadservice.domainLayer.JunctionType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import kotlin.test.assertEquals
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
	plugin = ["pretty", "html:target/cucumber-report.html"],
	features = ["src/test/resources"]
)
class JunctionCreationTest {

	private val useCase: UseCase = UseCase(DataSourceGatewayMock().getDataSourceGateway()!!)

	private lateinit var type: JunctionType
	private lateinit var coordinates: Pair<Coordinate, Coordinate>
	private lateinit var id: String

	@Given("a junction of type {string} with no outgoing roads, located in {int}, {int}")
	fun given(type: String, xPos: Int, yPos: Int) {
		val junctionType = JunctionType.valueOf(type)
		this.type = junctionType
		this.coordinates = Pair(Coordinate(xPos), Coordinate(yPos))
	}

	@When("is created")
	fun whenFun() {
		val result = useCase.addJunction(
			JunctionRequestModel(
				junctionType = type.ordinal,
				position = coordinates,
				outgoingRoads = ArrayList()
			)
		)
		if (result.isSuccess) {
			id = result.getOrNull()!!.junctionId
		} else {
			error(Exception("Failed junction creation"))
		}
	}

	@Then("should be saved correctly")
	fun then() {
		val junction = useCase.getJunction(id)
		if (junction.isFailure) error(Exception("No such junction existing"))
		val temp = junction.getOrNull()
		if (temp != null) {
			assertEquals(temp.junctionId, id)
		} else {
			error(Exception("Junction not retrieved"))
		}
	}
}
