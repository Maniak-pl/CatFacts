package pl.maniak.catfacts.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.maniak.catfacts.data.api.CatService
import pl.maniak.catfacts.model.CatFactRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {
    val catFactRepository: CatFactRepository
}

class DependencyInjectionImpl: DependencyInjection {

    override lateinit var catFactRepository: CatFactRepository

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
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        val catService = retrofit.create(CatService::class.java)

        catFactRepository = CatFactRepository(catService)
    }

    companion object {
        private val BASE_URL = "https://cat-fact.herokuapp.com"
    }
}