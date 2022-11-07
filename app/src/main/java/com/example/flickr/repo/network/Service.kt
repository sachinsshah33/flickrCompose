package com.example.flickr.repo.network

import com.example.flickr.repo.PhotosRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Service(private val endpoints: Endpoints) : IService {
    override suspend fun findByUsername(
        username: String
    ) = withContext(Dispatchers.IO) {
        endpoints.findByUsername(username = username)
    }

    override suspend fun photos(
        photosRequestModel: PhotosRequestModel
    ) = withContext(Dispatchers.IO) {
        val method =
            if (photosRequestModel.ownerAndTagsNotSpecified) NetworkConstants.APIMethod.PhotosAPIMethod.Recent() else NetworkConstants.APIMethod.PhotosAPIMethod.Search()
        endpoints.photos(
            method = method.toString(),
            user_id = photosRequestModel.ownerId,
            tags = if (photosRequestModel.tagsEmpty) null else photosRequestModel.tags.joinToString(
                NetworkConstants.tagsJoiner
            ),
            tag_mode = if (photosRequestModel.tagsEmpty) null else photosRequestModel.tag_mode?.toString(),
            page = photosRequestModel.page
        )
    }
}
