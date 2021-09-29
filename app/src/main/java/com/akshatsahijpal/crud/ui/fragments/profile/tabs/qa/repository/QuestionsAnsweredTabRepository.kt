package com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.adapter.PagingSourceForTabQA

class QuestionsAnsweredTabRepository {
    fun getAll(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 30,
            enablePlaceholders = true),
        pagingSourceFactory = {
            PagingSourceForTabQA(query)
        }
    ).liveData
}