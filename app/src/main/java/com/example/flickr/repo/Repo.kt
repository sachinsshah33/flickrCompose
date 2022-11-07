package com.example.flickr.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.flickr.repo.network.IService
import com.example.flickr.repo.network.NetworkConstants
import com.example.flickr.ui.models.PhotoLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repo(private val service: IService) : IRepo {
    override suspend fun findByUsername(username: String) = service.findByUsername(username)

    override fun photos(photosRequestModel: PhotosRequestModel): Flow<PagingData<PhotoLocal>> {
        return Pager(
            config = PagingConfig(
                pageSize = NetworkConstants.pageSize,
                //maxSize = NetworkConstants.pageSize,
                enablePlaceholders = true
            ),
            //remoteMediator = TaskRemoteMediator(query, groupId, db, taskApi),
            pagingSourceFactory = {
                PhotosPagingDataSource(service, photosRequestModel)
            }
        ).flow.map {
            it.map {
                PhotoLocal(it)
            }
        }
    }
}