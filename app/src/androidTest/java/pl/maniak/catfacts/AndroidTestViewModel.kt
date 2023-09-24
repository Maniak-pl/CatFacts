package pl.maniak.catfacts

import com.ww.roxie.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import pl.maniak.catfacts.presentation.CatFactAction
import pl.maniak.catfacts.presentation.CatFactState

class AndroidTestViewModel : BaseViewModel<CatFactAction, CatFactState>() {
    override val initialState: CatFactState = CatFactState()
    val testAction = TestObserver<CatFactAction>()
    val testState = PublishSubject.create<CatFactState>()

    init {
        actions.subscribe(testAction)
        disposables.add(
            testState.observeOn(AndroidSchedulers.mainThread())
                .subscribe(state::setValue)
        )
    }
}