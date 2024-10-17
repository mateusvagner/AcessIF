package com.mv.acessif.presentation.home.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mv.acessif.presentation.home.summary.summaryScreen
import com.mv.acessif.presentation.home.transcriptionDetail.transcriptionDetailScreen
import com.mv.acessif.presentation.home.transcriptions.transcriptionsScreen
import com.mv.acessif.presentation.root.RootStartDestination
import kotlinx.serialization.Serializable

@Serializable
object HomeNavGraph : RootStartDestination

fun NavGraphBuilder.homeNavGraph(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<HomeNavGraph> {
        val navController = rememberNavController()

        HomeNavGraph(
            modifier = modifier,
            navController = navController,
            rootNavController = rootNavController,
        )
    }
}

@Composable
fun HomeNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    Scaffold { innerPadding ->
        NavHost(
            startDestination = HomeScreen,
            navController = navController,
        ) {
            homeScreen(
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                navController = navController,
                rootNavController = rootNavController,
            )

            transcriptionsScreen(
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                navController = navController,
                rootNavController = rootNavController,
            )

            transcriptionDetailScreen(
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                navController = navController,
                rootNavController = rootNavController,
            )

            summaryScreen(
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                navController = navController,
                rootNavController = rootNavController,
            )
        }
    }
}
