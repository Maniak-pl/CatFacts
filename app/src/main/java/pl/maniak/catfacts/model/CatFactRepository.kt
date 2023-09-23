package pl.maniak.catfacts.model

import io.reactivex.Single
import pl.maniak.catfacts.data.api.CatService
import kotlin.random.Random

class CatFactRepository(private val catService: CatService) {

    fun getFact(): Single<String> {
        return catService.getFacts()
            .map { list ->
                val randomInt = if (list.size > 1) {
                    Random.nextInt(0, list.size - 1)
                } else {
                    0
                }
                list[randomInt].text
            }
    }
}