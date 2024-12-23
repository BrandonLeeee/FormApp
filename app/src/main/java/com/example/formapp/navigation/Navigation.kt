package com.example.formapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.formapp.FormViewModel
import com.example.formapp.ui.components.BottomNavBar
import com.example.formapp.ui.screens.FormPage
import com.example.formapp.ui.screens.FormEntries
import com.example.formapp.ui.screens.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val viewModel = FormViewModel()

    val navController = rememberNavController()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(bottomBar = { BottomNavBar(navController) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
                composable(Routes.SplashScreen.route) {
                    SplashScreen {
                        navController.navigate(Routes.FormPage.route) {
                            popUpTo(Routes.SplashScreen.route) { inclusive = true }
                        }
                    }
                }
                
                composable(Routes.FormPage.route){
                    FormPage(modifier = Modifier, viewModel)
                }
                composable(Routes.FormEntries.route){
                    FormEntries(viewModel)
                }
                
            }
        }
    }
}