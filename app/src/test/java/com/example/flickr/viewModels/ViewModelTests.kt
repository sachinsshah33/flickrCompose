package com.example.flickr.viewModels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.flickr.repo.network.models.UserMetaData
import com.example.flickr.repo.network.models.UserResponse
import com.example.flickr.repo.network.models.UserUsernameResponse
import com.example.flickr.useCases.PhotosUseCase
import com.example.flickr.useCases.UsersUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var usersUseCase: UsersUseCase

    @MockK
    private lateinit var photosUseCase: PhotosUseCase
    private lateinit var viewModel: ViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ViewModel(usersUseCase, photosUseCase)
    }

    //todo, look at using: https://github.com/square/okhttp/tree/master/mockwebserver
    /*@Test
    fun `refresh photos`() = runTest {
        viewModel.fetchPhotos()
        assertEquals()
    }*/

    //https://stackoverflow.com/questions/62975755/how-to-only-test-the-retrofit-request-param-in-mockwebserver-without-actually-ex
    //todo, look at testing retrofit url parameters


    @Test
    fun `addTag Updates Array`() = runBlocking {
        viewModel.addTag("dog")
        assertEquals(viewModel.photosRequestModel.value?.tags?.count(), 1)
    }

    @Test
    fun `correct UserId From Username`() = runBlocking {
        val username = "username"
        val userId = "32840300@N08"
        Mockito.`when`(usersUseCase.findByUsername(username)).thenReturn(
            Response.success(
                UserResponse(
                    UserMetaData("", userId, UserUsernameResponse(""))
                )
            )
        )
        viewModel.changeOwnerId(username)

        verify(usersUseCase, times(1)).findByUsername(username)
        assertEquals(viewModel.photosRequestModel.value?.ownerId, userId)
    }
}