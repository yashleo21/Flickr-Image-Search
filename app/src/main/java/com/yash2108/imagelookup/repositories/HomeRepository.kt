package com.yash2108.openissuesreader.repositories

import android.util.Log
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.database.dao.HomeDao
import com.yash2108.openissuesreader.models.HomeDataSource
import com.yash2108.openissuesreader.models.ResultUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepository constructor(
    private val localDataSource: HomeDataSource,
    private val remoteDataSource: HomeDataSource,
    private val dao: HomeDao
) {

    private val TAG = HomeRepository::class.java.simpleName

    suspend fun getPhotos(
        query: String?,
        page: Long?,
        pageSize: Long?
    ): Flow<ResultUI<List<FlickrDataObject>>> {
        return flow {
            if (query?.isBlank() == true && page == 1) {
                emit(fetchLocalData(query, page, pageSize))
            }

            if (query?.isNotBlank() == true) {
                emit(ResultUI.loading())
                val result = remoteDataSource.getData(query, page, pageSize)

                if (result.isNotEmpty()) {
                    if (page == 1) {
                        dao.deleteAllRcords()
                    }
                    dao.insertAllRecords(result)
                }

                emit(ResultUI.success(result))
            }
        }
    }

    private suspend fun fetchLocalData(
        query: String?,
        page: Long?,
        pageSize: Long?
    ): ResultUI<List<FlickrDataObject>> = localDataSource.getData(query, page, pageSize).let {
        Log.d(TAG, "Found data: ${it.size}")
        return ResultUI.success(it)
    }

}