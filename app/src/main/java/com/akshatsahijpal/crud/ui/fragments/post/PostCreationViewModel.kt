package com.akshatsahijpal.crud.ui.fragments.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.repository.post.PostCreationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    private var repo: PostCreationRepository,
) : ViewModel() {
    private var _liveData: MutableLiveData<String> = MutableLiveData()
    var liveData: LiveData<String> = _liveData
    fun uploadData(uplData: PostFeedData) {
        viewModelScope.async {
            var ref = repo.upload(uplData)
            if(ref?.path!=null){
                _liveData.value = ref?.path
            }
        }
    }

}