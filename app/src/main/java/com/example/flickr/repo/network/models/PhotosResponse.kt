package com.example.flickr.repo.network.models


data class PhotosResponse(
    val photos: PhotosMetaData
)

data class PhotosMetaData(
    val page: Int,
    val pages: Int,
    val photo: List<PhotoResponse>
)

data class PhotoResponse(
    val id: String,
    val owner: String,
    val ownername: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val tags: String
)
