package com.example.weatherapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapplication.api.NetworkResponse
import com.example.weatherapplication.api.weatherModel
import com.example.weatherapplication.ui.GooogleAds
import com.example.weatherapplication.ui.theme.LightBlue
import com.example.weatherapplication.ui.theme.Purple

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(modifier : Modifier = Modifier.verticalScroll(rememberScrollState(), true), vewModel : WeatherVewModel)
{
    var city by remember { mutableStateOf("") }
    val weatherResult = vewModel.weatherResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val gradientColors = listOf(Cyan, LightBlue, Purple )
    val randomCity = listOf("mumbai ", "pune" , "bhopal", "delhi")

    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
           horizontalAlignment = Alignment.CenterHorizontally)
    {
        TopAppBar(title = { Text(text = "Weather App") })

        Row(Modifier.fillMaxWidth()
                    .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
                {
                    OutlinedTextField(modifier = Modifier.weight(1f),
                                      value = city,
                                      onValueChange = {
                                          city = it
                                      },
                                      label = {
                                          Text(text = "Search for any location")
                                      })
                    IconButton(onClick = {
                        vewModel.getData(city)
                        keyboardController?.hide()
                    })
                    {
                        Icon(imageVector = Icons.Default.Search,
                             contentDescription = "Search ")
                    }

                }

             if (weatherResult.value == null ){

                 Column(Modifier.fillMaxSize(),
                      verticalArrangement = Arrangement.Center,
                      horizontalAlignment = Alignment.CenterHorizontally)
                 {
                     GooogleAds()
                   Icon(painter = painterResource(id = R.drawable.cloudy),
                        contentDescription = "weather")

                  Text(text = " Hello, what is the weather in ${randomCity.random()}",
                        style = TextStyle(fontWeight = FontWeight.Bold,
                                          fontStyle = FontStyle.Italic,
                                          fontSize = 22.sp,
                                          brush = Brush.linearGradient(colors = gradientColors)))
               }
             }

                when (val result = weatherResult.value)
                {
                    is NetworkResponse.Error->
                    {
                        Text(text = result.message)
                    }

                    NetworkResponse.Loading->
                    {
                        CircularProgressIndicator()
                    }

                    is NetworkResponse.Success->
                    {
                        WeatherDetails(data = result.data)
                    }
                    null-> {}
                }
            }
}


@Composable
fun WeatherDetails(data : weatherModel) {
    Column(
        modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
          ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
           ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
                )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.region+", "+data.location.country, fontSize = 18.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = " ${data.current.temp_c} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
            )

        Text(text = "Feels like ${data.current.cloud }% cloud")
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
            contentDescription = "Condition icon ",
            )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
            )
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(
                modifier = Modifier.fillMaxWidth()
                  ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                   ) {
                    WeatherKeyVal("Humidity",data.current.humidity)
                    WeatherKeyVal("Wind Speed",data.current.wind_kph+" km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                   ) {
                    WeatherKeyVal("UV",data.current.uv)
                    WeatherKeyVal("Participation",data.current.precip_mm+" mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                   ) {
                    WeatherKeyVal("Local Time",data.location.localtime.split(" ")[1])
                    WeatherKeyVal("Local Date",data.location.localtime.split(" ")[0])
                }
            }
        }
    }
}


@Composable
fun WeatherKeyVal(key : String, value : String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
          )
    {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}