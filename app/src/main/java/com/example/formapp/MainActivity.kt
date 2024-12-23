package com.example.formapp

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.formapp.navigation.AppNavigation
import com.example.formapp.ui.theme.FormAppTheme
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Force light theme only
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For API 29+, explicitly disable force dark
            val config = resources.configuration
            config.uiMode = config.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()
            resources.updateConfiguration(config, resources.displayMetrics)
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        setContent {
            FormAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

