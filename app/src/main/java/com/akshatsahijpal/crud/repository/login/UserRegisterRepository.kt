package com.akshatsahijpal.crud.repository.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.akshatsahijpal.crud.data.UserProfileData
import com.google.firebase.firestore.FirebaseFirestore


class UserRegisterRepository constructor(private var context: Context) {
    var db = FirebaseFirestore.getInstance()
    suspend fun uploadData(data: UserProfileData) {
        db.collection("users")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }
}