package com.nevi.pokemonswiki.compose.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class SnackBarUiState(
    val isVisible: Boolean = false,
    val errorMessage: Int = 0
)

open class BaseViewModel : ViewModel() {

    var snackBarUiState: SnackBarUiState by mutableStateOf(SnackBarUiState())
        private set

    fun dismissSnackBar(){
        snackBarUiState = snackBarUiState.copy(
            isVisible = false,
            errorMessage = 0
        )
    }

    fun showSnackBar(errorMessage: Int) {
        snackBarUiState = snackBarUiState.copy(
            isVisible = true,
            errorMessage = errorMessage
        )
    }
}