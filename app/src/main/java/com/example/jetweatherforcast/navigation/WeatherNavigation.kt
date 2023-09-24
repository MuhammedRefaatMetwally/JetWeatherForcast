package com.example.jetweatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweatherforcast.screens.search.SearchScreen
import com.example.jetweatherforcast.screens.about.AboutScreen
import com.example.jetweatherforcast.screens.favorits.FavoritesScreen
import com.example.jetweatherforcast.screens.main.MainScreen
import com.example.jetweatherforcast.screens.main.MainViewModel
import com.example.jetweatherforcast.screens.settings.SettingsScreen
import com.example.jetweatherforcast.screens.splash.WeatherSplashScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherNavigation() {
    val navController  = rememberNavController()
    
    NavHost(navController = navController , startDestination = WeatherScreens.SplashScreen.name ){

        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        //www.google.com/cityname="seattle"
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                })){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel = mainViewModel,
                    city = city)
            }


        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name){
            FavoritesScreen(navController = navController)
        }

    }
}