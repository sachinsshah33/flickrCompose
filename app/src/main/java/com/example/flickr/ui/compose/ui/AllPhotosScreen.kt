package com.example.flickr.ui.compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flickr.ui.compose.ui.components.ChipGroup
import com.example.flickr.ui.models.PhotoLocal
import com.example.flickr.viewModels.ViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun AllPhotosScreen(
    onImageClicked: (PhotoLocal) -> Unit,
    onUserClicked: (PhotoLocal) -> Unit,
    viewModel: ViewModel
) {
    val photosRequestModelCompose = remember { viewModel.photosRequestModelCompose }
    val photos = viewModel.photos(photosRequestModelCompose.value)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        PhotosList(photos, onImageClicked, onUserClicked)
    }
}


@Composable
fun PhotosList(
    photos: Flow<PagingData<PhotoLocal>>,
    onImageClicked: (PhotoLocal) -> Unit,
    onUserClicked: (PhotoLocal) -> Unit
) {
    val lazyItems = photos.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)//GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(lazyItems.itemCount)
        {
            lazyItems[it]?.let {
                PhotoItem(it, onImageClicked, onUserClicked)
            }
        }
    }
    /*LazyColumn {
        items(lazyItems) {
            PhotoItem(it!!, onImageClicked)
        }

        lazyItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }*/
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(
    photo: PhotoLocal,
    onImageClicked: (PhotoLocal) -> Unit,
    onUserClicked: (PhotoLocal) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onImageClicked.invoke(photo)
            },
        //elevation = CardElevation(defaultElevation = 10.dp)
    ) {
        ConstraintLayout {
            // Create references for the composables to constrain
            val (photoImage, user, tags) = createRefs()

            GlideImage(
                modifier = Modifier.constrainAs(photoImage) {
                    top.linkTo(parent.top, margin = 16.dp)
                },
                model = photo.photoUrl,
                contentScale = ContentScale.Crop,
                contentDescription = photo.photoTitle
            )

            Card(
                modifier = Modifier
                    .constrainAs(user) {
                        top.linkTo(photoImage.bottom, margin = 16.dp)
                    }
                    .fillMaxWidth()
                    .padding(6.dp)
                    .clickable {
                        onUserClicked.invoke(photo)
                    },
                //elevation = CardElevation(defaultElevation = 10.dp)
            ) {
                ConstraintLayout {
                    val (username, userProfileImage) = createRefs()
                    GlideImage(
                        modifier = Modifier
                            .size(24.dp)
                            .constrainAs(userProfileImage) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        model = photo.ownerProfileUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = photo.ownerUsername
                    )
                    Text(photo.ownerUsername, Modifier.constrainAs(username) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(userProfileImage.end)
                        end.linkTo(parent.end)
                    })
                }
            }



            ChipGroup(
                modifier = Modifier.constrainAs(tags) {
                    top.linkTo(user.bottom, margin = 16.dp)
                },
                tags = ArrayList(photo.tags)
            )
        }
    }
}