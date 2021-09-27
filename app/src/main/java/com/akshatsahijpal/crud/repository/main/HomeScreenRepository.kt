package com.akshatsahijpal.crud.repository.main

import android.util.Log
import com.akshatsahijpal.crud.util.Constants.POST
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class HomeScreenRepository {
    private val db = Firebase.firestore
    suspend fun cultivateData(): List<DocumentSnapshot>? {
        return try {
            val snap = db.collection(POST)
                .get().await()
            Log.d("Result ->>>>>>>>>>>>", snap.documents.toString())
            snap.documents
        }catch (e: Exception){
            Log.d("Error ->>>>>>>>>>>>", e.toString())
            null
        }
    }
}