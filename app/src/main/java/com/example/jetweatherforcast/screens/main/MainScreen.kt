package com.example.jetweatherforcast.screens.main

import ChatAlertDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforcast.data.DataOrException
import com.example.jetweatherforcast.model.WeatherItem
import com.example.jetweatherforcast.model.WeatherResponse
import com.example.jetweatherforcast.navigation.WeatherScreens
import com.example.jetweatherforcast.screens.settings.SettingsViewModel
import com.example.jetweatherforcast.utils.formatDate
import com.example.jetweatherforcast.utils.formatDecimals
import com.example.jetweatherforcast.widgets.HumidityWindPressureRow
import com.example.jetweatherforcast.widgets.SunsetSunRiseRow
import com.example.jetweatherforcast.widgets.WeatherAppBar
import com.example.jetweatherforcast.widgets.WeatherDetailRow
import com.example.jetweatherforcast.widgets.WeatherStateImage
import retrofit2.Call


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String? = "Cairo"
) {

    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (unitFromDb.isNotEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeather(
                cityName = curCity,
                units = unit
            )
        }.value

        Log.d("result ", weatherData.data.toString())

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(
                weather = weatherData.data!!, navController,
                isImperial = isImperial
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    weather: WeatherResponse, navController: NavController, isImperial: Boolean
) {

    Scaffold(topBar = {
        Surface(shadowElevation = 6.dp) {
            WeatherAppBar(
                title = weather.city?.name + " ,${weather.city?.country}",
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)

                },
            ) {
                Log.d("TAG", "MainScaffold: Button Clicked")
            }
        }

    }) {
        it
        MainContent(data = weather, isImperial = isImperial)

    }
}

@Composable
fun MainContent(data: WeatherResponse, isImperial: Boolean) {
    val weatherItem = data.list?.get(0)
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem?.weather?.get(0)?.icon}.png"

    Column(
        Modifier
            .padding(top = 64.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(weatherItem?.dt ?: 0), // Wed Nov 30
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem?.temp?.day ?: 0.0) + "º",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem?.weather?.get(0)?.main ?: "",
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(
            weather = data.list?.get(0) ?: WeatherItem(),
            isImperial = isImperial
        )
        Divider()
        SunsetSunRiseRow(weather = data.list?.get(0) ?: WeatherItem())
        Text(
            "This Week",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {

                items(data.list?.toList() ?: emptyList()) {
                    WeatherDetailRow(weather = it ?: WeatherItem())
                }

            }

        }

    }

}