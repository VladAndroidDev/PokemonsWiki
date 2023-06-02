package com.nevi.pokemonswiki.utils.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.nevi.pokemonswiki.R

@Composable
fun SnackBar(
    isSnackBarVisible: Boolean,
    snackBarMessageId: Int,
    snackBarHostState: SnackbarHostState,
    onDismiss: () -> Unit,
    actionLabel: String = stringResource(id = R.string.ok)
) {
    if (isSnackBarVisible) {
        val snackBarMessage = stringResource(id = snackBarMessageId)
        LaunchedEffect(Unit) {
            val result = snackBarHostState.showSnackbar(
                message = snackBarMessage,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                onDismiss()
            }
        }
    }
}

@Composable
fun SnackBar(
    isSnackBarVisible: Boolean,
    snackBarMessage: String,
    snackBarHostState: SnackbarHostState,
    onDismiss: () -> Unit,
    actionLabel: String = stringResource(id = R.string.ok)
) {
    if (isSnackBarVisible) {
        LaunchedEffect(Unit) {
            val result = snackBarHostState.showSnackbar(
                message = snackBarMessage,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                onDismiss()
            }
        }
    }
}