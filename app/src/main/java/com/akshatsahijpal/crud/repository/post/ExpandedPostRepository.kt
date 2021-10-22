package com.akshatsahijpal.crud.repository.post

import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.util.Constants
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ExpandedPostRepository {
    private var db = Firebase.firestore
    suspend fun getObjectBlockForID(uid: String): PostFeedData? {
        return db.collection(Constants.POST).document(uid)
            .get().await().toObject(PostFeedData::class.java)
    }
    suspend fun uploadCommentCipher(data: CommentData) : String {
        return db.collection(Constants.COMMENT).add(data).await().id
    }

    suspend fun PostWithCommentRef(it: String?, uid:String) {
        db.collection(Constants.POST).document(uid).update("commentIDs", FieldValue.arrayUnion(it)).await()
    }

    suspend fun getCommentData(uid: String):
            MutableList<CommentData> {
        val snap = db.collection(Constants.POST)
            .document(uid)
            .get().await().toObject(PostFeedData::class.java)
        val dataSet = mutableListOf<CommentData>()
        for (x in snap?.commentIDs!!){
            db.collection(Constants.COMMENT).document(x)
                .get().await()
                .toObject(CommentData::class.java)?.let { dataSet.add(it) }
        }
        return dataSet
    }
}