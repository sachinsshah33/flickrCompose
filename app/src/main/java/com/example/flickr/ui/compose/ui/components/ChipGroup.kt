package com.example.flickr.ui.compose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    tags: ArrayList<String>,
    onTagSelected: ((String) -> Unit)? = null,
) {
    Column(modifier = modifier.padding(8.dp)) {
        LazyRow {
            items(tags) {
                Chip(
                    name = it,
                    onTagSelected = {
                        onTagSelected?.invoke(it)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Chip(
    name: String = "Chip",
    onTagSelected: (String) -> Unit = {},
    enabled: Boolean? = null
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (enabled == true) Color.Green else Color.White
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = true,
                onValueChange = {
                    onTagSelected(name)
                }
            )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}