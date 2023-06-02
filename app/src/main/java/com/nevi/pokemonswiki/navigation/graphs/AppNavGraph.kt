package com.nevi.pokemonswiki.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nevi.pokemonswiki.compose.bottom_bar.BottomBarScreen
import com.nevi.pokemonswiki.compose.pokemon_detail.PokemonDetailScreen
import com.nevi.pokemonswiki.navigation.AppArgs.POKEMON_ID_ARG
import com.nevi.pokemonswiki.navigation.AppDestinations
import com.nevi.pokemonswiki.navigation.AppNavigationActions

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.BOTTOM_BAR_ROUTE,
    navigationActions: AppNavigationActions = AppNavigationActions(navController)
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = AppDestinations.BOTTOM_BAR_ROUTE
        ) {
            BottomBarScreen(navigateToPokemonDetail = navigationActions::navigateToPokemonDetailScreen)
        }
        composable(
            route = AppDestinations.POKEMON_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(
                    name = POKEMON_ID_ARG
                ) {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            val id = entry.arguments?.getInt(POKEMON_ID_ARG)!!
            PokemonDetailScreen(pokemonId = id, navigateUp = navigationActions::popUpPokemonDetailScreen)
        }
    }
}