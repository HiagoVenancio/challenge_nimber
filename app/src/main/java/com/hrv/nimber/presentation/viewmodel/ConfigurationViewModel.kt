package com.hrv.nimber.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor() : ViewModel() {

    private val _topBarConfig = MutableStateFlow<List<TopBarConfig>>(listOf())
    val topBarConfig: StateFlow<List<TopBarConfig>> = _topBarConfig

    fun mainScreenSetUp() {
         _topBarConfig.value = emptyList()
    }

    fun displayBackButton() {
        viewModelScope.launch {
            //_topBarConfig.value. add(TopBarConfig.DisplayBackButton)
            _topBarConfig.value += TopBarConfig.DisplayBackButton
        }
    }

    fun hideBackButton() {
        viewModelScope.launch {
            _topBarConfig.value -= TopBarConfig.DisplayBackButton
        }
    }

    fun displayRightButton() {
        viewModelScope.launch {
             _topBarConfig.value += TopBarConfig.DisplayRightButton
        }
    }

    fun hideRightButton() {
        viewModelScope.launch {
            _topBarConfig.value -= TopBarConfig.HideBackButton
        }
    }
}

sealed class TopBarConfig {
    object DisplayBackButton : TopBarConfig()
    object HideBackButton : TopBarConfig()
    object DisplayRightButton : TopBarConfig()
    object HideRightButton : TopBarConfig()
}
