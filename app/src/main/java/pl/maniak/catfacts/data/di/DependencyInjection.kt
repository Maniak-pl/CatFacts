package pl.maniak.catfacts.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ww.roxie.BaseViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.maniak.catfacts.data.api.CatService
import pl.maniak.catfacts.model.CatFactRepository
import pl.maniak.catfacts.presentation.CatFactAction
import pl.maniak.catfacts.presentation.CatFactState
import pl.maniak.catfacts.presentation.CatFactUseCaseImpl
import pl.maniak.catfacts.presentation.CatFactViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {
    val catFactViewModel: BaseViewModel<CatFactAction, CatFactState>
}

class DependencyInjectionImpl(apiUrl: String): DependencyInjection {

    override val catFactViewModel: BaseViewModel<CatFactAction, CatFactState>

    init {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        val catService = retrofit.create(CatService::class.java)

        val catFactRepository = CatFactRepository(catService)
        val catFactUseCase = CatFactUseCaseImpl(catFactRepository)
        catFactViewModel = CatFactViewModel(catFactUseCase)
    }
}