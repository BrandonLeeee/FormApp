package com.example.formapp.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.formapp.navigation.Routes

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(Routes.FormPage, Routes.FormEntries)

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val showBottomBar =
        currentRoute != Routes.SplashScreen.route && currentRoute != Routes.FormDetails.route

    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically { it } + fadeIn()
    ) {
        if (showBottomBar) {
            BottomNavigation(
                modifier = Modifier
                    .height(72.dp)
                    .background(
                        color = Color(0xFF1976D2),
                        shape = RoundedCornerShape(32.dp)
                    ),
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White,
                elevation = 8.dp
            ) {
                items.forEach { screen ->
                    val isSelected = currentRoute?.startsWith(screen.route) == true

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon!!,
                                contentDescription = screen.titlePage,
                                tint = if (isSelected) Color.White else Color.LightGray,
                                modifier = Modifier.size(if (isSelected) 36.dp else 28.dp) // Larger icons
                            )
                        },
                        label = {
                            Text(
                                text = screen.titlePage,
                                style = TextStyle(
                                    color = if (isSelected) Color.White else Color.LightGray,
                                    fontSize = if (isSelected) 18.sp else 14.sp, // Bold for selected, normal for unselected
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = true // Always show the label below the icon
                    )
                }
            }
        }
    }
}
