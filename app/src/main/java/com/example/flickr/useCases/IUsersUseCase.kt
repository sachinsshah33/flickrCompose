package com.example.flickr.useCases

import com.example.flickr.repo.network.models.UserResponse
import retrofit2.Response

interface IUsersUseCase {
    suspend fun findByUsername(username: String): Response<UserResponse>
}