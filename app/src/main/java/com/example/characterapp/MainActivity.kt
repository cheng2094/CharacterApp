package com.example.characterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.characterapp.view.CharacterScreen
import com.example.characterapp.ui.theme.CharacterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharacterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    CharacterScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
