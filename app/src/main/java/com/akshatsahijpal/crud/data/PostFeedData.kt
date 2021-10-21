package com.akshatsahijpal.crud.data

data class PostFeedData(
    var postUserName: String? = "",
    var postProfileName: String? = "",
    var postUploadTime: String? = "",
    var postProfilePicture: String? = "",
    var postMainParagraph: String? = "",
    var postAddPhoto: String? = "",
    var postLikes: Int = 0,
    var postCommentNumbers: Int = 0,
    var postSharesNumber: Int = 0,
    var documentID: String? = "default",
)