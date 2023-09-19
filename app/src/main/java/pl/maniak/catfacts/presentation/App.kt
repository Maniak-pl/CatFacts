package pl.maniak.catfacts.presentation

import android.app.Application
import android.content.Context
import pl.maniak.catfacts.data.di.DependencyInjection
import pl.maniak.catfacts.data.di.DependencyInjectionImpl

class App: Application() {

    val di: DependencyInjection by lazy {
        DependencyInjectionImpl()
    }
}

val Context.di: DependencyInjection
get() = (this.applicationContext as App).di