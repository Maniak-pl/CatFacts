package pl.maniak.catfacts.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.maniak.catfacts.data.CatService
import pl.maniak.catfacts.R
import pl.maniak.catfacts.data.api.CatFact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var catService: CatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        catService = retrofit.create(CatService::class.java)

        val getFactButton = findViewById<Button>(R.id.getFactButton)
        val loadingIndicator = findViewById<ProgressBar>(R.id.progressBar)
        val errorView = findViewById<TextView>(R.id.errorView)
        val catFactView = findViewById<TextView>(R.id.catFactView)

        getFactButton.setOnClickListener {
            val call = catService.getFacts()
            loadingIndicator.isVisible = true
            getFactButton.isEnabled = false

            call.enqueue(object : Callback<List<CatFact>> {
                override fun onFailure(call: Call<List<CatFact>>, t: Throwable) {
                    loadingIndicator.isVisible = false
                    getFactButton.isEnabled = true
                    errorView.isVisible = true
                }

                override fun onResponse(
                    call: Call<List<CatFact>>,
                    response: retrofit2.Response<List<CatFact>>
                ) {
                    loadingIndicator.isVisible = false
                    getFactButton.isEnabled = true
                    errorView.isVisible = false

                    val randomInt = Random.nextInt(0, response.body()?.size!!.minus(1))
                    catFactView.text = response.body()?.get(randomInt)?.text
                }
            })
        }
    }

    companion object {
        private val BASE_URL = "https://cat-fact.herokuapp.com"
    }
}
