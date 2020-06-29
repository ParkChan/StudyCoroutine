package com.chan.ui.bookmark

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chan.R
import com.chan.common.base.BaseFragment
import com.chan.common.viewmodel.BookmarkEventViewModel
import com.chan.databinding.FragmentBookmarkBinding
import com.chan.ui.bookmark.adapter.BookmarkAdapter
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.bookmark.viewmodel.BookmarkViewModel
import com.chan.ui.detail.ProductDetailActivityContract
import com.chan.ui.detail.ProductDetailContractData
import com.orhanobut.logger.Logger

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(
    R.layout.fragment_bookmark
) {
    private val activityResultLauncher: ActivityResultLauncher<ProductDetailContractData> =
        registerForActivityResult(
            ProductDetailActivityContract()
        ) { result: ProductDetailContractData? ->
            Logger.d("activity result >>> $result")
            //북마크 리스트 갱신처리
            binding.bookmarkViewModel?.lastRequestSortType?.let { selectAllBookmarkList(it) }
            result?.productModel?.let {
                binding.bookmarkViewModel?.isBookmarkCanceled(
                    binding.root.context,
                    it
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        iniViewModelObserve()
        initLayout()
        initListener()

    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        binding.bookmarkViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return BookmarkViewModel(
                        BookmarkRepository()
                    ) as T
                }
            }).get(BookmarkViewModel::class.java)

        binding.bookmarkEventViewModel = activity?.let {
            ViewModelProvider(it).get(BookmarkEventViewModel::class.java)
        }

        binding.rvBookmark.adapter = BookmarkAdapter(binding.bookmarkViewModel as BookmarkViewModel)
    }

    private fun iniViewModelObserve() {
        val owner = this
        //DB 리스트 조회 성공
        binding.bookmarkViewModel?.bookmarkListData?.observe(
            viewLifecycleOwner,
            Observer {
                Logger.d("bookmarkViewModel observe listData $it")
                if (it.isNotEmpty()) {
                    binding.rvBookmark.visibility = View.VISIBLE
                    binding.tvEmptyList.visibility = View.GONE

                } else {
                    binding.rvBookmark.visibility = View.GONE
                    binding.tvEmptyList.visibility = View.VISIBLE
                }

            })

        //DB 리스트 조회 실패
        binding.bookmarkViewModel?.errorMessage?.observe(
            viewLifecycleOwner,
            Observer {
                    Toast.makeText(
                        owner.context,
                        getString(R.string.common_toast_msg_network_error),
                        Toast.LENGTH_SHORT
                    ).show()
            })

        binding.bookmarkViewModel?.sortType?.observe(
            viewLifecycleOwner,
            Observer {
                Logger.d("bookmarkViewModel observe sortType $it")
                selectAllBookmarkList(it)
            })

        //즐겨찾기 삭제
        binding.bookmarkViewModel?.removeBookmarkModel?.observe(
            viewLifecycleOwner,
            Observer { bookmarkModel ->
                Logger.d("bookmarkViewModel observe removeData $bookmarkModel")
                context?.let { context ->
                    binding.bookmarkViewModel?.removeBookmark(
                        context,
                        bookmarkModel
                    )
                    binding.bookmarkEventViewModel?.deletedObserveBookmark(bookmarkModel)
                }
            })

        //상세화면에서 북마크 체크여부
        binding.bookmarkViewModel?.existsProductModel?.observe(viewLifecycleOwner,
            Observer {
                binding.bookmarkEventViewModel?.deletedObserveBookmark(it)
            })

        binding.bookmarkViewModel?.productItemSelected?.observe(
            viewLifecycleOwner,
            Observer {
                activityResultLauncher.launch(
                    ProductDetailContractData(
                        it.position,
                        it.productModel
                    )
                )
            })
    }

    private fun initLayout() {
        binding.rvBookmark.visibility = View.GONE
        binding.tvEmptyList.visibility = View.VISIBLE
    }

    private fun initListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Logger.d("radioGroup checkedId $checkedId")

            when (checkedId) {
                R.id.radio_reg_date_desc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.RegDateDesc
                    )
                }
                R.id.radio_reg_date_asc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.RegDateAsc
                    )
                }
                R.id.radio_review_desc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.ReviewRatingDesc
                    )
                }
                R.id.radio_review_asc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.ReviewRatingAsc
                    )
                }
            }
            Handler().postDelayed({
                binding.rvBookmark.layoutManager?.scrollToPosition(0)
            }, 200)

        }
    }

    private fun selectAllBookmarkList(sortType: BookmarkSortType) {
        binding.bookmarkViewModel?.selectAll(binding.root.context, sortType)
    }

    fun listUpdate() {
        binding.bookmarkViewModel?.lastRequestSortType?.let { selectAllBookmarkList(it) }
    }

}
