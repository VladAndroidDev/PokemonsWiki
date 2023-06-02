package com.nevi.pokemonswiki.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.nevi.pokemonswiki.R
import com.nevi.pokemonswiki.navigation.BottomBarDestinations.BERRIES_ROUTE
import com.nevi.pokemonswiki.navigation.BottomBarDestinations.POKEMONS_ROUTE
import com.nevi.pokemonswiki.navigation.BottomBarScreens.BERRIES_SCREEN
import com.nevi.pokemonswiki.navigation.BottomBarScreens.POKEMONS_SCREEN


private object BottomBarScreens {

    const val POKEMONS_SCREEN = "pokemons"
    const val BERRIES_SCREEN = "berries"

}

object BottomBarArgs {


}

object BottomBarDestinations {

    const val POKEMONS_ROUTE = POKEMONS_SCREEN
    const val BERRIES_ROUTE = BERRIES_SCREEN

}

sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {

    object PokemonsScreen : BottomBarScreen(
        route = POKEMONS_ROUTE,
        title = R.string.pokemons,
        icon = Icons.Default.Person
    )

    object BerriesScreen : BottomBarScreen(
        route = BERRIES_ROUTE,
        title = R.string.berries,
        icon = Icons.Default.Fastfood
    )

}

class BottomBarNavigationActions(private val navHostController: NavHostController) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

}