package com.hendyapp.mblcore.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hendyapp.mblcore.data.source.local.entity.BookVolumeFavoriteEntity

@Dao
interface BookVolumeFavoriteDao {
    @Query("SELECT * FROM bookvolumefavorite")
    suspend fun getAllFavoriteBooks(): List<BookVolumeFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(book: BookVolumeFavoriteEntity)

    @Delete
    suspend fun deleteFavoriteBook(book: BookVolumeFavoriteEntity)
}