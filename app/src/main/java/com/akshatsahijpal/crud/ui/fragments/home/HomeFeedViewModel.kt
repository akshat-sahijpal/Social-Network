package com.akshatsahijpal.crud.ui.fragments.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.repository.main.HomeScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private var repo: HomeScreenRepository
): ViewModel() {
    private var _binder: MutableLiveData<MutableList<PostFeedData>> = MutableLiveData()
    private var binder: LiveData<MutableList<PostFeedData>> = _binder
    fun connectWithBackend() {
        viewModelScope.launch {
            val data = repo.cultivateData()
            Log.d(">>>>>", data.toString())
            val dataSet = mutableListOf<PostFeedData>()
            if (data != null) {
                for(i in data){
                    i.toObject(PostFeedData::class.java)?.let { dataSet.add(it) }
                    Log.d(">>>>>", dataSet.toString())
                }
                _binder.value = dataSet
            }
        }
    }
    fun getDataSet() = this.binder
}