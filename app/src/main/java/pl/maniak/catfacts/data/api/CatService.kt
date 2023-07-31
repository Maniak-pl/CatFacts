package pl.maniak.catfacts.data.api

import io.reactivex.Single
import retrofit2.http.GET

interface CatService {

    @GET("facts")
    fun getFacts(): Single<List<CatFact>>
}