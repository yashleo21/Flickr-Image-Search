package com.yash2108.openissuesreader.models

import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.database.dao.HomeDao
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(val dao: HomeDao): HomeDataSource {

    override suspend fun getData(query: String?, page: Long?, pageSize: Long?): List<FlickrDataObject> {
        return dao.getAllRecords()
    }

}