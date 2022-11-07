package com.example.flickr.ui.extensions

import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.example.flickr.ui.utils.GridSpacingItemDecoration
import kotlin.math.roundToInt

fun RecyclerView.addGridItemDecoration(
    columns: Int = 2,
    sidePadding: Int = 48.dp.value.roundToInt()
) {
    addItemDecoration(GridSpacingItemDecoration(columns, sidePadding))
}