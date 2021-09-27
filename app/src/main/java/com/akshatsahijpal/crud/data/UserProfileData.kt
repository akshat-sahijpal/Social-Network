package com.akshatsahijpal.crud.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

data class UserProfileData(
    var fullName: String? = "",
    var location: String? = "",
    var description: String? = "",
    var googleAccount: String
)
