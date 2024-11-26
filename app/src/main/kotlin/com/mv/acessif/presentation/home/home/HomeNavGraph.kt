package com.mv.acessif.presentation.home.home

import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.mv.acessif.presentation.home.summary.summaryRoute
import com.mv.acessif.presentation.home.transcriptionDetail.transcriptionDetailRoute
import com.mv.acessif.presentation.home.transcriptions.transcriptionsRoute
import com.mv.acessif.presentation.navigation.model.Destination
import com.mv.acessif.presentation.root.RootGraph
import kotlinx.serialization.Serializable

sealed interface HomeGraph : Destination {
    @Serializable
    data class HomeRoute(
        val userName: String,
    ) : HomeGraph

    @Serializable
    data object TranscriptionsRoute : HomeGraph

    @Serializable
    data class TranscriptionDetailRoute(
        val transcriptionId: Int,
        @StringRes val originScreen: Int,
    ) : HomeGraph

    @Serializable
    data class SummaryRoute(
        val transcriptionId: Int,
    ) : HomeGraph
}

fun NavGraphBuilder.homeGraphRoute(
    modifier: Modifier,
    navController: NavHostController,
) {
    navigation<RootGraph.HomeGraph>(
        startDestination = HomeGraph.HomeRoute(userName = ""),
    ) {
        homeRoute(
            modifier = modifier,
        )

        transcriptionsRoute(
            modifier = modifier,
        )

        transcriptionDetailRoute(
            modifier = modifier,
            navController = navController,
        )

        summaryRoute(
            modifier = modifier,
        )
    }
}
