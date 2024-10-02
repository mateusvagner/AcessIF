package com.mv.acessif.presentation.home.summary

import com.mv.acessif.domain.Summary
import com.mv.acessif.presentation.UiText

data class SummaryScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val summary: Summary? = null,
)
