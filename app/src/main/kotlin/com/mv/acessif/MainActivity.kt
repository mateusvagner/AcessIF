package com.mv.acessif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mv.acessif.presentation.root.RootNavGraph
import com.mv.acessif.ui.theme.AcessIFTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val rootNavHostController = rememberNavController()
            AcessIFTheme {
                RootNavGraph(rootNavController = rootNavHostController)
            }
        }
    }
}
