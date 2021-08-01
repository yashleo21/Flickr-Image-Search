package com.yash2108.openissuesreader.network.service

import com.yash2108.imagelookup.models.HomeDataResponse
import com.yash2108.openissuesreader.network.Endpoints
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitAPI {

    @GET(Endpoints.IMAGE_SEARCH)
    suspend fun getPhotos(@QueryMap map: HashMap<String, Any>): HomeDataResponse?

}