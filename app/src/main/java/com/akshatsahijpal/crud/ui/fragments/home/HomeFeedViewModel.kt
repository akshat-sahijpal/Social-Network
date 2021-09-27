package com.akshatsahijpal.crud.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.repository.main.HomeScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private var repo: HomeScreenRepository,
) : ViewModel() {
    private var _binder: MutableLiveData<LiveData<PagingData<PostFeedData>>> = MutableLiveData()
    var binder: LiveData<LiveData<PagingData<PostFeedData>>> = _binder
    fun grabData() {
        _binder.value = repo.constructRecycler().cachedIn(viewModelScope)
    }
}