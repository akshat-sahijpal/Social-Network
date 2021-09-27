package com.akshatsahijpal.crud.repository.main

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.akshatsahijpal.crud.util.Constants.POST
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class HomeScreenRepository {
    private val db = Firebase.firestore
    fun constructRecycler() =
        Pager(
            config = PagingConfig(pageSize = 10, maxSize = 30, enablePlaceholders = false),
            pagingSourceFactory = {
                HomeScreenDataSource()
            }
        ).liveData


    suspend fun cultivateData(): List<DocumentSnapshot>? {
        return try {
            val snap = db.collection(POST)
                .get().await()
            snap.documents
        } catch (e: Exception) {
            Log.d("Error ->>>>>>>>>>>>", e.toString())
            null
        }
    }
}