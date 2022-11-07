package com.example.flickr.repo

import com.example.flickr.repo.network.NetworkConstants

data class PhotosRequestModel(
    var ownerId: String? = null,
    val tags: ArrayList<String> = arrayListOf(),
    var tag_mode: NetworkConstants.TagMode? = null,
    //val text: String? = null,
    var page: Int? = 1
) {
    val tagsEmpty get() = tags.isEmpty()
    val ownerAndTagsNotSpecified get() = ownerId.isNullOrEmpty() && tagsEmpty

    fun setTagMode(all: Boolean) {
        tag_mode = if (all) NetworkConstants.TagMode.All else NetworkConstants.TagMode.Any
    }
}