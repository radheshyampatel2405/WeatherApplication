package com.example.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme
import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)

        val weatherVewModel=
                ViewModelProvider(this)[WeatherVewModel::class.java]
//        MobileAds.initialize(this@MainActivity) {}
        enableEdgeToEdge()
        setContent {
            WeatherApplicationTheme {
                MobileAds.initialize(this@MainActivity) {}
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    HomeScreen(modifier = Modifier.padding(innerPadding), weatherVewModel)
                }
            }
        }
    }
}