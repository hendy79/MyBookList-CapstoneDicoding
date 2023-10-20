package com.hendyapp.mblcore.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hendyapp.mblcore.data.source.local.entity.BookVolumeFavoriteEntity

@Database(entities = [BookVolumeFavoriteEntity::class], version = 1, exportSchema = false)
abstract class BookVolumeFavoriteDb: RoomDatabase() {
    abstract fun bookVolumeFavoriteDao(): BookVolumeFavoriteDao
}