package pl.maniak.catfacts

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import pl.maniak.catfacts.presentation.CatFactActivity

class CatFactActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java)

    val mockWebServer = MockWebServer()
    val resources: Resources
        get() = activityRule.activity.resources

    val fact = "Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea."
    val json = "[\n" +
            "  {\n" +
            "    \"status\": {\n" +
            "      \"verified\": true,\n" +
            "      \"sentCount\": 1\n" +
            "    },\n" +
            "    \"_id\": \"58e009390aac31001185ed10\",\n" +
            "    \"user\": \"58e007480aac31001185ecef\",\n" +
            "    \"text\": \"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea.\",\n" +
            "    \"__v\": 0,\n" +
            "    \"source\": \"user\",\n" +
            "    \"updatedAt\": \"2020-08-23T20:20:01.611Z\",\n" +
            "    \"type\": \"cat\",\n" +
            "    \"createdAt\": \"2018-03-04T21:20:02.979Z\",\n" +
            "    \"deleted\": false,\n" +
            "    \"used\": false\n" +
            "  }\n" +
            "]"

    @Test
    fun initialState() {
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText("Push the button!")))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
    }

    @Test
    fun getFactButtonPressed_factLoadedSuccess() {
        val mockResponse = MockResponse()
        mockResponse.setBody(json)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText("Push the button!")))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))

        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
    }

    @Test
    fun getFactButtonPressed_errorOccurred() {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(400)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))

        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))
    }

}