package com.example.flickr.useCases

import androidx.paging.PagingData
import com.example.flickr.repo.PhotosRequestModel
import com.example.flickr.ui.models.PhotoLocal
import kotlinx.coroutines.flow.Flow

interface IPhotosUseCase {
    fun photos(photosRequestModel: PhotosRequestModel): Flow<PagingData<PhotoLocal>>
}