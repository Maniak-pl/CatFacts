package pl.maniak.catfacts.presentation

sealed class CatFactChange {

    object Loading: CatFactChange()
    data class FactLoaded(val fact: String) : CatFactChange()
    object Error : CatFactChange()
}