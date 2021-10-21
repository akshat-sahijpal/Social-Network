package com.akshatsahijpal.crud.repository.post

import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.util.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ExpandedPostRepository {
    private var db = Firebase.firestore
    suspend fun getObjectBlockForID(uid: String): PostFeedData? {
        return db.collection(Constants.POST).document(uid)
            .get().await().toObject(PostFeedData::class.java)
    }
}