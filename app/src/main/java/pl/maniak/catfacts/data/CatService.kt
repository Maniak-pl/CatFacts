package pl.maniak.catfacts.data

import pl.maniak.catfacts.data.api.CatFact
import retrofit2.Call
import retrofit2.http.GET

interface CatService {

    @GET("facts")
    fun getFacts(): Call<List<CatFact>>
}