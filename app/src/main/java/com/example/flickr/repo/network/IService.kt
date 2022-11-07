package com.example.flickr.repo.network

import com.example.flickr.repo.PhotosRequestModel
import com.example.flickr.repo.network.models.PhotosResponse
import com.example.flickr.repo.network.models.UserResponse
import retrofit2.Response

interface IService {
    suspend fun findByUsername(username: String): Response<UserResponse>
    suspend fun photos(photosRequestModel: PhotosRequestModel): Response<PhotosResponse>
}