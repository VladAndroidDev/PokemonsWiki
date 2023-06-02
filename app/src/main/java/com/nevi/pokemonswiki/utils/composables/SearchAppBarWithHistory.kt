package com.nevi.pokemonswiki.utils.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nevi.pokemonswiki.R
import com.nevi.pokemonswiki.model.history.entities.History

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBarWithHistory(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    histories: List<History>,
    hint: String = stringResource(id = R.string.search)
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        trailingIcon = {
            if (active) {
                IconButton(onClick = { if (query == "") onActiveChange(false) else onQueryChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        placeholder = {
            Text(text = hint)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(histories) { item: History ->
                HistoryListItem(history = item, onHistoryClicked = onSearch)
            }
        }
    }
}

@Composable
fun HistoryListItem(history: History, onHistoryClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onHistoryClicked(history.history) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.padding(end = 12.dp),
            imageVector = Icons.Default.History,
            contentDescription = ""
        )
        Text(text = history.history)
    }
}

