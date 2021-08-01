package com.yash2108.openissuesreader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.database.dao.HomeDao


@Database(
    entities = arrayOf(FlickrDataObject::class),
    version = Constants.VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeDao(): HomeDao
}