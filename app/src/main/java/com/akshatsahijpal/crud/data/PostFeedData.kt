package com.akshatsahijpal.crud.data

data class PostFeedData(
    var postUserName: String,
    var postProfileName: String,
    var postUploadTime: String,
    var postProfilePicture: String?,
    var postMainParagraph: String?,
    var postAddPhoto: String?,
    var postLikes: Int,
    var postCommentNumbers: Int,
    var postSharesNumber: Int
)
