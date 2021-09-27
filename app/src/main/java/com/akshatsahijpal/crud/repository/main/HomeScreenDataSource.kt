package com.akshatsahijpal.crud.repository.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.util.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

const val STARTING_PAGE = 1

class HomeScreenDataSource : PagingSource<Int, PostFeedData>() {
    private val db = Firebase.firestore
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostFeedData> {
        var position = params.key ?: STARTING_PAGE
        return try {
            val snap = db.collection(Constants.POST)
                .get().await()
            var response = snap.documents
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