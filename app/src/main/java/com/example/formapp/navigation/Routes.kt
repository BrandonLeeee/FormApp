package com.example.formapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(
    val route: String,
    val titlePage: String,
    val icon: ImageVector?
) {
    object LoginPage : Routes("loginPage", "Login Page", null)
    object FormPage : Routes("formPage", "Form Page", Icons.Default.Home)
    object FormEntries : Routes("formEntries", "Form Entries", Icons.Default.List)
    object FormDetails : Routes("formDetails", "Form Details", null)
    object SplashScreen : Routes("splashScreen", "Splash Screen", null)
}
