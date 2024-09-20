package com.mv.acessif.presentation.home.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mv.acessif.presentation.home.newTranscription.newTranscriptionScreen
import com.mv.acessif.presentation.home.transcriptionDetail.transcriptionDetailScreen
import com.mv.acessif.presentation.home.transcriptions.TranscriptionsScreen
import com.mv.acessif.presentation.home.transcriptions.transcriptionsScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeNavGraph

fun NavGraphBuilder.homeNavGraph(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<HomeNavGraph> {
        HomeNavGraph(
            modifier = modifier,
            rootNavController = rootNavController,
        )
    }
}

@Composable
fun HomeNavGraph(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = modifier,
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.padding(contentPadding),
            startDestination = HomeScreen,
            navController = navController,
        ) {
            homeScreen(
                modifier = Modifier,
                navController = navController,
                rootNavController = rootNavController,
            )

            transcriptionsScreen(
                modifier = Modifier,
                navController = navController,
                rootNavController = rootNavController,
            )

            newTranscriptionScreen(
                modifier = Modifier,
                navController = navController,
                rootNavController = rootNavController,
            )

            transcriptionDetailScreen(
                modifier = Modifier,
                navController = navController,
                rootNavController = rootNavController,
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navBackStackEntry: NavBackStackEntry?,
    navController: NavHostController,
) {
    NavigationBar {
        items.forEach { item ->
            val isSelected = item.tab.javaClass.name == navBackStackEntry?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector =
                            if (isSelected) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                        contentDescription = item.title,
                    )
                },
                onClick = {
                    navController.navigate(item.tab) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

// TODO Create property bottom bar
data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val tab: HomeGraphTab,
)

val items =
    listOf(
        BottomNavigationItem(
            title = "Home Screen",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            tab = HomeScreen,
        ),
        BottomNavigationItem(
            title = "Transcription Screen",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            tab = TranscriptionsScreen,
        ),
    )
