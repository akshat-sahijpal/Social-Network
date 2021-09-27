package com.akshatsahijpal.crud.remote

import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.util.Constants.postPost
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostUploader {
    private val db: FirebaseFirestore = Firebase.firestore
    fun uploadPost(postData: PostFeedData){
        db.collection(postPost)
    }
 }