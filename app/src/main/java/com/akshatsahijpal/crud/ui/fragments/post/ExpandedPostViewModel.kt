package com.akshatsahijpal.crud.ui.fragments.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshatsahijpal.crud.data.CommentData
import com.akshatsahijpal.crud.data.PostFeedData
import com.akshatsahijpal.crud.repository.post.ExpandedPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpandedPostViewModel @Inject constructor(
    private var repo: ExpandedPostRepository
) : ViewModel() {
    private var _data: MutableLiveData<PostFeedData> = MutableLiveData()
    private var _commentViewData: MutableLiveData<List<CommentData>> = MutableLiveData()
    private var commentViewData: LiveData<List<CommentData>> = _commentViewData
    private var _commentData: MutableLiveData<String> = MutableLiveData()
    private var commentData: LiveData<String> = _commentData

    private var data: LiveData<PostFeedData> = _data
    fun checkForDataWithUid(uid: String) {
        viewModelScope.launch {
            _data.value = repo.getObjectBlockForID(uid)
        }
    }

    fun listenData() = data
    fun commentUploader(data: CommentData) {
        viewModelScope.launch {
           _commentData.value = repo.uploadCommentCipher(data)
        }
    }
    fun listenCommentData() = commentData
    fun updatePostWithCommentRef(it: String?, uid:String) {
        viewModelScope.launch {
            repo.PostWithCommentRef(it, uid)
        }
    }

    fun getCommentIDs(uid: String) {
        viewModelScope.launch {
            _commentViewData.value = repo.getCommentData(uid)
        }
    }
    fun watchCommentIDs() = commentViewData
}