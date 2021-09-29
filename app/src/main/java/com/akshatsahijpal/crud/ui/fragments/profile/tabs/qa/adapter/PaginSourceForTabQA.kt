package com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.repository.main.STARTING_PAGE
import com.akshatsahijpal.crud.util.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

const val START_PAGE = 1
class PagingSourceForTabQA constructor(private var query:String): PagingSource<Int, PostFeedData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostFeedData> {
        val db = Firebase.firestore
        var position = params.key?: START_PAGE
        return try {
            val response = db.collection(Constants.POST).whereEqualTo("postUserName", query)
                .get().await().documents
            val dataSet = mutableListOf<PostFeedData>()
            for (i in response) {
                i.toObject(PostFeedData::class.java)?.let { dataSet.add(it) }
            }
            LoadResult.Page(
                data = dataSet,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (dataSet.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostFeedData>): Int? {
        TODO("Not yet implemented")
    }
}
