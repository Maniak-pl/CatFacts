package pl.maniak.catfacts.presentation

import com.ww.roxie.BaseAction

sealed class CatFactAction : BaseAction {
    object GetFactButtonClicked : CatFactAction()
}