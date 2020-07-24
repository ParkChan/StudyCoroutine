package com.chan.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)

    @Delete
    suspend fun delete(vararg obj: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(obj: T)

}