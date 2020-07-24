package com.chan.db

import androidx.room.Dao
import androidx.room.Query
import com.chan.ui.bookmark.model.BookmarkModel


@Dao
interface BookmarkDao : BaseDao<BookmarkModel> {

    @Query("SELECT * FROM bookmarkTable")
    suspend fun selectAll(): List<BookmarkModel>

    //등록일 내림차순
    @Query("SELECT * FROM bookmarkTable ORDER BY regTimeStamp DESC")
    suspend fun selectAllRegDateDesc(): List<BookmarkModel>

    //등록일 오름차순
    @Query("SELECT * FROM bookmarkTable ORDER BY regTimeStamp ASC")
    suspend fun selectAllRegDateAsc(): List<BookmarkModel>

    //리뷰 내림차순
    @Query("SELECT * FROM bookmarkTable ORDER BY rate DESC")
    suspend fun selectAllReviewDesc(): List<BookmarkModel>

    //리뷰 오름차순
    @Query("SELECT * FROM bookmarkTable ORDER BY rate ASC")
    suspend fun selectAllReviewAsc(): List<BookmarkModel>

    //즐겨찾기 등록 유무
    @Query("SELECT EXISTS (SELECT * FROM bookmarkTable WHERE id = :productId)")
    suspend fun selectProductExists(productId: String): Int

}

