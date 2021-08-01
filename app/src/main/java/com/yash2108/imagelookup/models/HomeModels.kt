package com.yash2108.imagelookup.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yash2108.openissuesreader.database.Constants


data class HomeDataResponse(var photos: FlickrDataContainer?)


data class FlickrDataContainer(
    var page: Long?,
    var pages: Long?,
    var perpage: Long?,
    var total: Long?,
    var photo: List<FlickrDataObject>
)

@Entity(tableName = Constants.PHOTOS_TABLE)
data class FlickrDataObject(
    @PrimaryKey
    var id: String,
    var farm: String?,
    var server: String?,
    var secret: String?,
    var isFavorite: Boolean = false
)