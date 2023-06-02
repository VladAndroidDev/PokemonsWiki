package com.nevi.pokemonswiki.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nevi.pokemonswiki.compose.bottom_bar.berries.BerriesListScreen
import com.nevi.pokemonswiki.compose.bottom_bar.pokemons.PokemonsListScreen
import com.nevi.pokemonswiki.navigation.BottomBarDestinations

@Composable
fun BottomBarNavGraph(
    parentPaddingValues: PaddingValues,
    navHostController: NavHostController,
    startDestination: String = BottomBarDestinations.POKEMONS_ROUTE,
    navigateToPokemonDetail: (Int) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(
            route = BottomBarDestinations.POKEMONS_ROUTE
        ) {
            PokemonsListScreen(parentPaddingValues = parentPaddingValues, navigateToPokemonDetail = navigateToPokemonDetail)
        }
        composable(
            route = BottomBarDestinations.BERRIES_ROUTE
        ) {
            BerriesListScreen(parentPaddingValues = parentPaddingValues)
        }
    }
}