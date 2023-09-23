package pl.maniak.catfacts.catfact

import androidx.lifecycle.Observer
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.*
import pl.maniak.catfacts.RxTestSchedulerRule
import pl.maniak.catfacts.presentation.CatFactAction
import pl.maniak.catfacts.presentation.CatFactState
import pl.maniak.catfacts.presentation.CatFactUseCase
import pl.maniak.catfacts.presentation.CatFactViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

class CatFactViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule: RxTestSchedulerRule = RxTestSchedulerRule()

    private val initialState = CatFactState(loading = false)
    private val loadingState = CatFactState(loading = true)

    private val catFactUseCase = mock<CatFactUseCase>()
    private val observer = mock<Observer<CatFactState>>()

    lateinit var testSubject: CatFactViewModel

    @Before
    fun setup() {
        testSubject = CatFactViewModel(catFactUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given fact successfully loaded, when action GetCatFact is received, then State contains fact`() {
        // GIVEN
        val fact = "Programmers love cats."
        val successState = CatFactState(loading = false, catFact = fact)

        whenever(catFactUseCase.getFact()).thenReturn(Single.just(fact))

        // WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successState)
        }

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given fact failed to load, when action GetFact received, then state contains error`() {
        // GIVEN
        val errorState = CatFactState(displayError = true)
        whenever(catFactUseCase.getFact()).thenReturn(Single.error(java.lang.RuntimeException()))

        // WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testSchedulerRule.triggerActions()

        //  THEN
        inOrder(observer) {
            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }

        verifyNoMoreInteractions(observer)
    }
}