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
                    .height(48.dp)
                    .background(
                        color = Color(0xFF1976D2),
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        ) // Rounded corners
                    ),
                backgroundColor = Color.White,
                contentColor = Color(0xFF1976D2)
            ) {
                items.forEach { screen ->
                    val isSelected = currentRoute == screen.route

                    BottomNavigationItem(
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                screen.icon?.let {
                                    Icon(
                                        it,
                                        contentDescription = "screen.titlePage",
                                        tint = if (isSelected) Color(0xFF1976D2) else Color.LightGray,
                                        modifier = Modifier.size(if (isSelected) 28.dp else 24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                        },
                        label = {
                            Text(
                                text = screen.titlePage,
                                style = TextStyle(
                                    color = if (isSelected) Color(0xFF1976D2) else Color.LightGray,
                                    fontSize = if (isSelected) 15.sp else 12.sp,
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
                        }
                    )
                }
            }
        }
    }
}
