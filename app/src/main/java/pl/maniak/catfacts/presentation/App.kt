package pl.maniak.catfacts.presentation

import android.app.Application
import android.content.Context
import pl.maniak.catfacts.R
import pl.maniak.catfacts.data.di.DependencyInjection
import pl.maniak.catfacts.data.di.DependencyInjectionImpl

open class App : Application() {

    open val di: DependencyInjection by lazy {
        DependencyInjectionImpl(getString(R.string.api_url))
    }
}

val Context.di: DependencyInjection
    get() = (this.applicationContext as App).di