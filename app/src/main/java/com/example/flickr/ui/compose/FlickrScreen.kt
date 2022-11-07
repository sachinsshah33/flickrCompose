package com.example.flickr.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flickr.R
import com.example.flickr.ui.compose.ui.AllPhotosScreen
import com.example.flickr.ui.compose.ui.PhotoDetailsScreen
import com.example.flickr.ui.compose.ui.components.FlickrSearchAppBar
import com.example.flickr.ui.models.PhotoLocal
import com.example.flickr.viewModels.ViewModel
import com.squareup.moshi.Moshi

enum class FlickrScreen(@StringRes val title: Int) {
    AllPhotos(title = R.string.app_name),
    PhotoDetails(title = R.string.screen_photo_details),
    UserPhotos(title = R.string.screen_user_photos),
}


@Composable
fun FlickrSubAppBar(
    currentScreen: FlickrScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,


        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@ExperimentalComposeUiApi
@Composable
fun FlickrApp(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = FlickrScreen.valueOf(
        backStackEntry?.destination?.route ?: FlickrScreen.AllPhotos.name
    )

    //val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            if (currentScreen == FlickrScreen.AllPhotos) {
                FlickrSearchAppBar(
                    viewModel
                )
            } else {
                FlickrSubAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }

        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = FlickrScreen.AllPhotos.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = FlickrScreen.AllPhotos.name) {
                AllPhotosScreen(
                    onImageClicked = {
                        /*navController.currentBackStackEntry?.arguments?.apply {
                            putParcelable("photoLocal", it)
                        }*/
                        //navController.navigate(FlickrScreen.PhotoDetails.name)

                        val moshi = Moshi.Builder().build()
                        val jsonAdapter = moshi.adapter(PhotoLocal::class.java).lenient()
                        val photoLocalJson = jsonAdapter.toJson(it)
                        val ROUTE_PHOTO_DETAILS =
                            "${FlickrScreen.PhotoDetails.name}/photoLocal=\"adasd\""
                        //navController.navigate(ROUTE_PHOTO_DETAILS)
                        val index = 5234
                        navController.navigate("sdjfi/$index")

                    },
                    onUserClicked = {
                        /*navController.currentBackStackEntry?.arguments?.apply {
                            putParcelable("photoLocal", it)
                        }
                        navController.navigate(FlickrScreen.UserPhotos.name)*/


                        /*val moshi = Moshi.Builder().build()
                        val jsonAdapter = moshi.adapter(PhotoLocal::class.java).lenient()
                        val photoLocalJson = jsonAdapter.toJson(it)
                        val ROUTE_USER_PHOTOS = "${FlickrScreen.UserPhotos.name}/$photoLocalJson"
                        navController.navigate(ROUTE_USER_PHOTOS)*/


                        //https://stackoverflow.com/questions/67121433/how-to-pass-object-in-navigation-in-jetpack-compose
                        /*val moshi = Moshi.Builder().build()
                        val jsonAdapter = moshi.adapter(PhotoLocal::class.java).lenient()
                        val photoLocalJson = jsonAdapter.toJson(it)
                        val ROUTE_USER_PHOTOS = "${FlickrScreen.UserPhotos.name}/photoLocal=${photoLocalJson}"
                        navController.navigate(ROUTE_USER_PHOTOS)*/


                        navController.navigate(FlickrScreen.UserPhotos.name)
                    },
                    viewModel
                )
            }
            composable(
                "sdjfi/{ijgsdf}",
                arguments = listOf(navArgument("ijgsdf") { type = NavType.IntType })
            ) {
                //composable(route = "${FlickrScreen.PhotoDetails.name}/{photoLocal}", arguments = listOf(navArgument("photoLocal") { type = PhotoLocalNavType() })) {
                //val context = LocalContext.current


                /*val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(PhotoLocal::class.java)
                val photoLocal = jsonAdapter.fromJson(it.arguments?.getString("photoLocal"))!!*/

                val photoLocal = backStackEntry?.arguments?.getInt("ijgsdf")
                val photoLocal2 = it.arguments?.getInt("ijgsdf")

                /*val photoLocal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.arguments?.getParcelable("photoLocal", PhotoLocal::class.java)
                } else {
                    it.arguments?.getParcelable<PhotoLocal>("photoLocal")
                }*/

                PhotoDetailsScreen(
                    navController
                )
            }
            composable(route = FlickrScreen.UserPhotos.name) {
                val context = LocalContext.current


                /*val photoLocal = remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(navController.previousBackStackEntry?.arguments?.getParcelable("photoLocal", PhotoLocal::class.java)!!)
                    } else {
                        mutableStateOf(navController.previousBackStackEntry?.arguments?.getParcelable<PhotoLocal>("photoLocal")!!)
                    }
                }*/

                PhotoDetailsScreen(
                    navController
                )
            }
        }
    }
}