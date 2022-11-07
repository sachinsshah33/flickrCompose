package com.example.flickr.repo.network.models


data class UserResponse(
    val user: UserMetaData
)

data class UserMetaData(
    val id: String,
    val nsid: String,
    val username: UserUsernameResponse
)

data class UserUsernameResponse(
    val _content: String
)
