package com.example.movieappusingcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappusingcompose.screens.details.DetailScreen
import com.example.movieappusingcompose.screens.home.HomeScreen

@Composable
fun MovieNavigation(){

    val navController  = rememberNavController()
    NavHost(navController = navController,
        startDestination = NavigationScreens.HomeScreen.name){

        composable(NavigationScreens.HomeScreen.name){
            HomeScreen(navController)
        }

        composable(NavigationScreens.DetailsScreen.name+"/{movie}",
        arguments = listOf(navArgument("movie"){type = NavType.StringType})
        ){
            backStackEntry ->
            DetailScreen(navController, backStackEntry.arguments?.getString("movie"))
        }
    }
}
