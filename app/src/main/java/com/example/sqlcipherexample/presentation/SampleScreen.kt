package com.example.sqlcipherexample.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SampleScreen(viewModel: SampleViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Column {
        Button(onClick = viewModel::add) {
            Text(text = "Add")
        }
        LazyColumn {
            items(items = state, key = SampleUiModel::id) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(it.color),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = it.name)
                    IconButton(
                        onClick = { viewModel.delete(it.id) }
                    ) {
                        Icon(Icons.Default.Close, null)
                    }
                }
            }
        }
    }
}
