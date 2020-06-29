package com.chan.ui.bookmark.repository

import android.content.Context
import com.chan.ui.bookmark.BookmarkSortType
import com.chan.ui.bookmark.local.BookmarkDataSource
import com.chan.ui.bookmark.local.BookmarkDatabase
import com.chan.ui.bookmark.local.DataBaseResult
import com.chan.ui.bookmark.model.BookmarkModel
import com.chan.ui.home.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarkRepository : BookmarkDataSource {

    private val ioDispatcher = Dispatchers.IO

    override suspend fun selectBookmarkList(
        context: Context,
        sort: BookmarkSortType
    ): DataBaseResult<List<BookmarkModel>> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (sort) {
                    is BookmarkSortType.RegDateDesc -> {
                        DataBaseResult.Success(
                            BookmarkDatabase.getInstance(context).bookmarkDao()
                                .selectAllRegDateDesc()
                        )
                    }
                    is BookmarkSortType.RegDateAsc -> {
                        DataBaseResult.Success(
                            BookmarkDatabase.getInstance(context).bookmarkDao()
                                .selectAllRegDateAsc()
                        )

                    }

                    is BookmarkSortType.ReviewRatingDesc -> {
                        DataBaseResult.Success(
                            BookmarkDatabase.getInstance(context).bookmarkDao()
                                .selectAllReviewDesc()
                        )
                    }
                    is BookmarkSortType.ReviewRatingAsc -> {
                        DataBaseResult.Success(
                            BookmarkDatabase.getInstance(context).bookmarkDao().selectAllReviewAsc()
                        )
                    }
                }
            } catch (e: Exception) {
                DataBaseResult.Failure(e)
            }
        }

    override suspend fun isExists(
        context: Context,
        productModel: ProductModel
    ): DataBaseResult<Boolean> = withContext(ioDispatcher) {
        return@withContext try {
            val isExists = BookmarkDatabase.getInstance(context)
                .bookmarkDao().selectProductExists(productModel.id)
            when (isExists) {
                1 -> DataBaseResult.Success(true)
                else -> DataBaseResult.Success(false)
            }
        } catch (e: Exception) {
            DataBaseResult.Failure(e)
        }
    }

    override suspend fun insertBookMark(context: Context, model: BookmarkModel) =
        withContext(ioDispatcher) {
            return@withContext BookmarkDatabase.getInstance(context).bookmarkDao().insert(model)
        }

    override suspend fun deleteBookMark(context: Context, model: BookmarkModel) =
        withContext(ioDispatcher) {
            return@withContext BookmarkDatabase.getInstance(context).bookmarkDao().delete(model)
        }
}