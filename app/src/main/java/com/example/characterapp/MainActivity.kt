package com.example.characterapp

import com.example.characterapp.viewmodel.MenuViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.characterapp.view.CharacterScreen
import com.example.characterapp.ui.theme.CharacterAppTheme
import com.example.characterapp.view.MenuScreen
import com.example.characterapp.view.PlanetScreen
import dagger.hilt.android.AndroidEntryPoint


sealed class Screen(val route: String) {
    object MenuScreen : Screen("menu")
    object CharacterScreen : Screen("character")
    object PlanetScreen : Screen("planet")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharacterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    DragonBallBackground {
        NavHost(
            navController = navController,
            startDestination = Screen.MenuScreen.route,
            modifier = modifier
        ) {
            composable(Screen.MenuScreen.route) {
                val viewModel = hiltViewModel<MenuViewModel>()
                MenuScreen(
                    onNavigateToCharacter = { navController.navigate(Screen.CharacterScreen.route) },
                    onNavigateToPlanet = { navController.navigate(Screen.PlanetScreen.route) },
                    viewModel = viewModel
                )
            }
            composable(Screen.CharacterScreen.route) {
                CharacterScreen()
            }
            composable(Screen.PlanetScreen.route) {
                PlanetScreen()
            }
        }
    }
}

@Composable
fun DragonBallBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_dragonball),
                contentScale = ContentScale.Crop
            )
    ) {
        content()
    }
}

