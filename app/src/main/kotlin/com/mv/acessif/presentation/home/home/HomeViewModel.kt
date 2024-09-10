package com.mv.acessif.presentation.home.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel(
    private val userName: String,
) : ViewModel() {
    @Inject
    constructor(savedStateHandle: SavedStateHandle) : this(
        userName = "Mateus Vagner",
    )

    private val _name = MutableStateFlow(userName)
    val name = _name.asStateFlow()
}
