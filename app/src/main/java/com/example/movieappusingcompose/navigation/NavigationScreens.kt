package com.example.movieappusingcompose.navigation

enum class NavigationScreens {
        HomeScreen,
        DetailsScreen;

    companion object {
        fun fromRoute (route :String?) : NavigationScreens =
            when(route?.substringBefore("/")){
                HomeScreen.name -> HomeScreen
                DetailsScreen.name -> DetailsScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route not found")
            }
    }
    }


