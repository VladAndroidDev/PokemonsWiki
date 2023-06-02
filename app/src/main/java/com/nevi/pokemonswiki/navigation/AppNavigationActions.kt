package com.nevi.pokemonswiki.navigation

import androidx.navigation.NavHostController
import com.nevi.pokemonswiki.navigation.AppArgs.POKEMON_ID_ARG
import com.nevi.pokemonswiki.navigation.AppScreens.BOTTOM_BAR_SCREEN
import com.nevi.pokemonswiki.navigation.AppScreens.POKEMON_DETAIL_SCREEN

private object AppScreens {

    const val BOTTOM_BAR_SCREEN = "bottomBar"
    const val POKEMON_DETAIL_SCREEN = "pokemonDetail"

}

object AppArgs {
    const val POKEMON_ID_ARG = "pokemonIdArg"
}

object AppDestinations {

    const val BOTTOM_BAR_ROUTE = BOTTOM_BAR_SCREEN
    const val POKEMON_DETAIL_ROUTE = "$POKEMON_DETAIL_SCREEN/{$POKEMON_ID_ARG}"
}

class AppNavigationActions(private val navHostController: NavHostController) {

    fun navigateToPokemonDetailScreen(id: Int) {
        navHostController.navigate("$POKEMON_DETAIL_SCREEN/$id")
    }

    fun popUpPokemonDetailScreen(){
        navHostController.popBackStack()
    }

}