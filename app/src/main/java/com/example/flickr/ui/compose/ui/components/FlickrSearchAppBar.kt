package com.example.flickr.ui.compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.flickr.R
import com.example.flickr.repo.network.NetworkConstants
import com.example.flickr.viewModels.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//https://github.com/mitchtabian/MVVMRecipeApp/blob/b68c5a4c0244c1f49aff93d1d16ad3c5ad1f6886/app/src/main/java/com/codingwithmitch/mvvmrecipeapp/presentation/components/SearchAppBar.kt
//https://foso.github.io/Jetpack-Compose-Playground/material/badgedbox/
@ExperimentalComposeUiApi
@Composable
fun FlickrSearchAppBar(
    viewModel: ViewModel
) {
    val usernameQuery = viewModel.usernameQuery.value
    val photosRequestModelCompose = remember { viewModel.photosRequestModelCompose }
    val keyboardController = LocalSoftwareKeyboardController.current


    var showAddTagDialog by remember { mutableStateOf(false) }
    if (showAddTagDialog) {
        AddTagDialog(
            viewModel,
            onDismiss = {
                showAddTagDialog = !showAddTagDialog
            },
            onNegativeClick = {
                showAddTagDialog = !showAddTagDialog
            }
        ) {
            viewModel.addTag(it)
            showAddTagDialog = !showAddTagDialog
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(8.dp),
                    value = usernameQuery,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    label = { Text(text = stringResource(R.string.hint_enter_username)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val (menu) = createRefs()
                    IconButton(
                        modifier = Modifier
                            .constrainAs(menu) {
                                end.linkTo(parent.end)
                                linkTo(top = parent.top, bottom = parent.bottom)
                            },
                        onClick = {
                            showAddTagDialog = true
                        },
                    ) {
                        BadgedBox(badge = {
                            Badge {
                                Text(
                                    photosRequestModelCompose.value.tags.count().toString()
                                )
                            }
                        }) {
                            Icon(
                                Icons.Filled.FilterList,
                                contentDescription = "Filter"
                            )
                        }
                    }
                }
            }
        }
    }
}


/*@Composable
fun showAddTagDialog(viewModel: ViewModel){
    var showAddTagDialog by remember { mutableStateOf(false) }
    if (showAddTagDialog) {
        AddTagDialog(
            viewModel.photosRequestModel.value?.tags,
            onDismiss = {
                showAddTagDialog = !showAddTagDialog
            },
            onNegativeClick = {
                showAddTagDialog = !showAddTagDialog
            }
        ) {
            viewModel.addTag(it)
            showAddTagDialog = !showAddTagDialog
        }
    }
}*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTagDialog(
    viewModel: ViewModel,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (String) -> Unit
) {

    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val tags = viewModel.photosRequestModel.value?.tags
    val tagModeAll by remember { mutableStateOf(viewModel.photosRequestModel.value?.tag_mode == NetworkConstants.TagMode.All) }

    Dialog(onDismissRequest = onDismiss) {
        androidx.compose.material3.Card(
            //elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = stringResource(R.string.alert_title_add_tag),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                androidx.compose.material3.TextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = text,
                    //placeholder = stringResource(R.string.alert_hint_add_tag),
                    onValueChange = { text = it }
                )

                Row(Modifier.fillMaxWidth()) {
                    Chip(
                        name = NetworkConstants.TagMode.All.toString(),
                        onTagSelected = {
                            viewModel.changeTagMode(true)
                        },
                        enabled = tagModeAll
                    )
                    Chip(
                        name = NetworkConstants.TagMode.Any.toString(),
                        onTagSelected = {
                            viewModel.changeTagMode(false)
                        },
                        enabled = !tagModeAll
                    )
                }


                ChipGroup(
                    tags = tags ?: arrayListOf(),
                    onTagSelected = {
                        viewModel.removeTag(it)
                    }
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    androidx.compose.material3.TextButton(onClick = {
                        onPositiveClick(text)
                    }) {
                        Text(text = stringResource(R.string.button_add))
                    }
                }
            }
        }
    }

    //to autofocus the edittext in dialog
    LaunchedEffect(key1 = "autofocus") {
        scope.launch {
            //todo, find out when layout has finished drawaing and then autofocus
            delay(100)
            focusRequester.requestFocus()
        }
    }
}

