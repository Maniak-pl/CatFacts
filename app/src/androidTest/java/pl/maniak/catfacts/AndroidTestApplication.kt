package pl.maniak.catfacts

import pl.maniak.catfacts.data.di.DependencyInjection
import pl.maniak.catfacts.presentation.App

class AndroidTestApplication : App() {

    override val di = TestDependencyInjection
}

object TestDependencyInjection : DependencyInjection {
    override val catFactViewModel = AndroidTestViewModel()
}