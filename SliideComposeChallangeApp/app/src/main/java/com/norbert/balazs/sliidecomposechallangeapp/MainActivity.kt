package com.norbert.balazs.sliidecomposechallangeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                SliideComposeChallangeApp()
            }
        }
    }
}

@Composable
fun SliideComposeChallangeApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "greeting") {
        composable(route = "greeting") {
            Greeting("World!!")
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}