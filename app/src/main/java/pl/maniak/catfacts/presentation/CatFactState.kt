package pl.maniak.catfacts.presentation

import com.ww.roxie.BaseState

data class CatFactState(
    val loading: Boolean = false,
    val catFact: String = "",
    val displayError: Boolean = false
) : BaseState