package com.akshatsahijpal.crud.repository.post

import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.util.Constants.POST
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class PostCreationRepository {
    private val db = Firebase.firestore
    suspend fun upload(uplData: PostFeedData): DocumentReference? {
        return try {
            db.collection(POST)
                .add(uplData).await()
        }catch (e: Exception){
            null
        }
    }
}