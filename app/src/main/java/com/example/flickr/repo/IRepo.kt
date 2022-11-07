package com.example.flickr.repo

import androidx.paging.PagingData
import com.example.flickr.repo.network.models.UserResponse
import com.example.flickr.ui.models.PhotoLocal
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepo {
    suspend fun findByUsername(username: String): Response<UserResponse>
    fun photos(photosRequestModel: PhotosRequestModel): Flow<PagingData<PhotoLocal>>
}