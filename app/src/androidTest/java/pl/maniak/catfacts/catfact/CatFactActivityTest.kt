package pl.maniak.catfacts.catfact

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import pl.maniak.catfacts.R
import pl.maniak.catfacts.TestDependencyInjection
import pl.maniak.catfacts.presentation.CatFactAction
import pl.maniak.catfacts.presentation.CatFactActivity
import pl.maniak.catfacts.presentation.CatFactState

class CatFactActivityTest {
    private val viewModel = TestDependencyInjection.catFactViewModel

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java)

    private val resources: Resources
        get() = activityRule.activity.resources

    @Test
    fun initialState() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        assertEquals(viewModel.observableState.value, initialState)
    }

    @Test
    fun getFactButtonClickedAction() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.getFactButton)).perform(ViewActions.click())

        viewModel.testAction.assertValue(CatFactAction.GetFactButtonClicked)
    }

    @Test
    fun loadingState() {
        val initialState = CatFactState(loading = true)
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.push_the_button)))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.getFactButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun factLoadedState() {
        val fact = "Even cats need to be tested"
        val initialState = CatFactState(catFact = fact)
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
    }
}