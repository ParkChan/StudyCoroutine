package com.chan.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chan.ui.bookmark.model.BookmarkModel

@Database(
    entities = [BookmarkModel::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var instance: BookmarkDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): BookmarkDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        BookmarkDatabase::class.java, "BOOK_MARK_TABLE"
                    ).build()
                }
            }
            return instance!!
        }
    }
}