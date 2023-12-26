package com.konopelko.foodtestapp.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<UI_STATE, PARTIAL_UI_STATE, FRAGMENT_EVENT, INTENT>(
    initialState: UI_STATE
) : ViewModel() {

    private val intentsFlow = MutableSharedFlow<INTENT>()

    private val mutableUiState = MutableStateFlow(initialState)
    val uiState: StateFlow<UI_STATE> = mutableUiState

    private val fragmentEventChannel = Channel<FRAGMENT_EVENT>(Channel.BUFFERED)
    val fragmentEvent = fragmentEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            intentsFlow
                .flatMapConcat(::mapIntents)
                .catch { cause -> cause.printStackTrace() }
                .collect {
                    mutableUiState.value = reduceUiState(
                        previousState = uiState.value,
                        partialState = it
                    )
                }
        }
    }

    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            intentsFlow.emit(intent)
        }
    }

    protected fun publishFragmentEvent(event: FRAGMENT_EVENT) {
        viewModelScope.launch {
            fragmentEventChannel.send(event)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE
    ): UI_STATE
}