package com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.repository.QuestionsAnsweredTabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionsAnsweredTabViewModel @Inject constructor(
    private var repo: QuestionsAnsweredTabRepository
): ViewModel() {
    private var _data : MutableLiveData<LiveData<PagingData<PostFeedData>>> = MutableLiveData()
    var data: LiveData<LiveData<PagingData<PostFeedData>>> = _data
    fun searchAllPostFor(query: String) {
        _data.value = repo.getAll(query).cachedIn(viewModelScope)
    }
}