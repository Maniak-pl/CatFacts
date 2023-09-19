package pl.maniak.catfacts.presentation

import io.reactivex.Single
import pl.maniak.catfacts.model.CatFactRepository

interface CatFactUseCase {
    fun getFact(): Single<String>
}

class CatFactUseCaseImpl(private val catFactRepository: CatFactRepository) : CatFactUseCase {
    override fun getFact(): Single<String> {
        return catFactRepository.getFact()
    }
}