package com.yash2108.openissuesreader.models

import com.yash2108.imagelookup.models.FlickrDataObject

interface HomeDataSource {

    suspend fun getData(query: String?, page: Long?, pageSize: Long?): List<FlickrDataObject>

}