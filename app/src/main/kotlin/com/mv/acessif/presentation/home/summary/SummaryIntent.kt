package com.mv.acessif.presentation.home.summary

sealed interface SummaryIntent {
    data object OnNavigateBack : SummaryIntent

    data object OnTryAgain : SummaryIntent

    data object OnShareSummary : SummaryIntent
}
