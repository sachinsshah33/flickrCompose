package com.example.flickr.ui.models

import android.os.Parcelable
import com.example.flickr.repo.network.NetworkConstants
import com.example.flickr.repo.network.NetworkConstants.ownerProfileUrl
import com.example.flickr.repo.network.NetworkConstants.photoUrl
import com.example.flickr.repo.network.models.PhotoResponse
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class PhotoLocal(
    val photoId: String,
    val photoUrl: String,
    val photoTitle: String,
    val ownerId: String,
    val ownerUsername: String,
    val ownerProfileUrl: String,
    val tags: List<String>,
) : Parcelable {
    constructor(model: PhotoResponse) : this(
        photoId = model.id,
        photoUrl = model.photoUrl,
        photoTitle = model.title,
        ownerId = model.owner,
        ownerUsername = model.ownername,
        ownerProfileUrl = model.ownerProfileUrl,
        tags = model.tags.split(NetworkConstants.tagsDelimiter).filterNot { it.isEmpty() },
    )
}