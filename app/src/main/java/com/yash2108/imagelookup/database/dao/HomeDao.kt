package com.yash2108.openissuesreader.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.database.Constants

@Dao
interface HomeDao {

    @Insert
    suspend fun insert(flickrDataObject: FlickrDataObject)

    @Insert
    suspend fun insertAllRecords(flickrDataObject: List<FlickrDataObject>)

    @Query("DELETE FROM ${Constants.PHOTOS_TABLE}")
    suspend fun deleteAllRcords()

    @Query("SELECT * FROM ${Constants.PHOTOS_TABLE}")
    suspend fun getAllRecords(): List<FlickrDataObject>

}