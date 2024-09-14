package com.mv.acessif.presentation.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.data.local.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _onLogoutSuccess = Channel<Unit>()
    val onLogoutSuccess =
        _onLogoutSuccess.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

    fun logoutUser() {
        sharedPreferencesManager.clearAccessToken()

        viewModelScope.launch {
            _onLogoutSuccess.send(Unit)
        }
    }
}
