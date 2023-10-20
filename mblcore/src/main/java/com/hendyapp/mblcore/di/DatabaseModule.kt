package com.hendyapp.mblcore.di

import android.content.Context
import androidx.room.Room
import com.hendyapp.mblcore.BuildConfig
import com.hendyapp.mblcore.data.source.local.room.BookVolumeFavoriteDao
import com.hendyapp.mblcore.data.source.local.room.BookVolumeFavoriteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BookVolumeFavoriteDb {
        val passphrase = SQLiteDatabase.getBytes(BuildConfig.KEY_ROOM.toCharArray())
        return Room.databaseBuilder(
                context,
                BookVolumeFavoriteDb::class.java, "BookVolumeFavorite.db"
            )
            .fallbackToDestructiveMigration()
            .openHelperFactory(SupportFactory(passphrase))
            .build()
    }

    @Provides
    fun provideTourismDao(database: BookVolumeFavoriteDb): BookVolumeFavoriteDao =
        database.bookVolumeFavoriteDao()
}