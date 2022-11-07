package com.example.flickr.ui.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PhotoDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    /*val photoLocal = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(navController.previousBackStackEntry?.arguments?.getParcelable("photoLocal", PhotoLocal::class.java)!!)
        } else {
            mutableStateOf(navController.previousBackStackEntry?.arguments?.getParcelable<PhotoLocal>("photoLocal")!!)
        }
    }*/

    Column(modifier = modifier.padding(16.dp)) {
        Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(modifier = Modifier.weight(1f), onClick = {}) {
                Text("Make another network call and load PhotoDetails, or UserPhotos")
            }
        }
    }
}