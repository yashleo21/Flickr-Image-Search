package com.yash2108.openissuesreader.models

import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.imagelookup.network.RequestBuilder
import com.yash2108.openissuesreader.network.service.RetrofitAPI
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(val client: RetrofitAPI) : HomeDataSource {

    override suspend fun getData(
        query: String?,
        page: Long?,
        pageSize: Long?
    ): List<FlickrDataObject> {
        val request = RequestBuilder.buildPhotoRequest(query, page, pageSize)

        return try {
            val response = client.getPhotos(request)
            response?.photos?.photo ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

}