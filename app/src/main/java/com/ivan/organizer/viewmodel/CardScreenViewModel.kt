package com.ivan.organizer.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Immutable
data class CardModel(val id: Int, val title: String)

class CardsScreenViewModel : ViewModel() {

    private val _cards = MutableStateFlow(listOf<CardModel>())
    val cards: StateFlow<List<CardModel>> get() = _cards

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    init {
        getFakeData()
    }

    private fun getFakeData() {
        viewModelScope.launch(Dispatchers.Default) {
            val testList = arrayListOf<CardModel>()
            repeat(10) { testList += CardModel(id = it, title = "Card $it") }
            _cards.emit(testList)
        }
    }

    fun onItemExpanded(cardId: Int) {
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    fun onItemCollapsed(cardId: Int) {
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }
}