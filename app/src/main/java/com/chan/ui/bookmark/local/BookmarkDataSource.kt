package com.chan.ui.bookmark.local

import android.content.Context
import com.chan.ui.bookmark.BookmarkSortType
import com.chan.ui.bookmark.model.BookmarkModel
import com.chan.ui.home.model.ProductModel

interface BookmarkDataSource {

    suspend fun selectBookmarkList(
        context: Context,
        sort: BookmarkSortType
    ): DataBaseResult<List<BookmarkModel>>

    suspend fun isExists(
        context: Context,
        productModel: ProductModel
    ): DataBaseResult<Boolean>

    suspend fun insertBookMark(
        context: Context,
        model: BookmarkModel
    )

    suspend fun deleteBookMark(
        context: Context,
        model: BookmarkModel
    )
}