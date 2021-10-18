package com.akshatsahijpal.crud.adapter.home

import com.akshatsahijpal.crud.data.PostFeedData

data class CheckClickerForPost(var clickListener: (data: PostFeedData)->Unit)
