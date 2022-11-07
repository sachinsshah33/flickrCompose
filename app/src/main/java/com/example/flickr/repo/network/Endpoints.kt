package com.example.flickr.repo.network

import com.example.flickr.repo.network.models.PhotosResponse
import com.example.flickr.repo.network.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface Endpoints {

    @GET
    suspend fun findByUsername(
        @Url url: String = "",
        @Query(
            "method",
            encoded = true
        ) method: String = NetworkConstants.APIMethod.PeopleAPIMethod.FindByUsername.toString(),
        @Query("username", encoded = true) username: String
    ): Response<UserResponse>


    /*@GET("/?method=flickr.photos.getRecent")
    suspend fun photosRecent(@Query("page", encoded = true) page: String? = null): Response<PhotosResponse>*/
    @GET
    suspend fun photos(
        @Url url: String = "",
        @Query("method", encoded = true) method: String,
        @Query("user_id", encoded = true) user_id: String? = null,
        @Query("tags", encoded = true) tags: String? = null,
        @Query("tag_mode", encoded = true) tag_mode: String? = null,
        @Query("text", encoded = true) text: String? = null,
        @Query("page", encoded = true) page: Int? = null
    ): Response<PhotosResponse>
}
