package com.nevi.pokemonswiki.compose.bottom_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nevi.pokemonswiki.navigation.BottomBarNavigationActions
import com.nevi.pokemonswiki.navigation.BottomBarScreen
import com.nevi.pokemonswiki.navigation.graphs.BottomBarNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarScreen(
    navHostController: NavHostController = rememberNavController(),
    navigateToPokemonDetail: (Int) -> Unit
) {

    val bottomBarNavigationActions = remember {
        BottomBarNavigationActions(navHostController)
    }

    Scaffold(bottomBar = {
        BottomBar(
            navHostController = navHostController,
            onItemClicked = bottomBarNavigationActions::navigateTo
        )
    }) { paddingValues ->
        BottomBarNavGraph(
            parentPaddingValues = paddingValues,
            navHostController = navHostController,
            navigateToPokemonDetail = navigateToPokemonDetail
        )
    }
}

@Composable
fun BottomBarContent() {

}

@Composable
fun BottomBar(navHostController: NavHostController, onItemClicked: (String) -> Unit) {
    val screens = remember {
        listOf(
            BottomBarScreen.PokemonsScreen,
            BottomBarScreen.BerriesScreen
        )
    }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isBottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (isBottomBarDestination) {
        BottomAppBar {
            screens.forEach { item ->
                AddItem(
                    bottomBarScreen = item,
                    currentDestination = currentDestination,
                    onItemClicked = onItemClicked
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    bottomBarScreen: BottomBarScreen,
    currentDestination: NavDestination?,
    onItemClicked: (String) -> Unit
) {
    BottomNavigationItem(selected = currentDestination?.hierarchy?.any {
        it.route == bottomBarScreen.route
    } == true,
        onClick = { onItemClicked(bottomBarScreen.route) },
        icon = { Icon(imageVector = bottomBarScreen.icon, contentDescription = "") },
        label = {
            Text(text = stringResource(id = bottomBarScreen.title))
        }
    )
}