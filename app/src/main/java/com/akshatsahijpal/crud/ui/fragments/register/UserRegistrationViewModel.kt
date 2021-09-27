package com.akshatsahijpal.crud.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshatsahijpal.crud.data.UserProfileData
import com.akshatsahijpal.crud.repository.login.UserRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(private var repo: UserRegisterRepository) :
    ViewModel() {
    fun uploadData(userProfileData: UserProfileData) {
        viewModelScope.launch { repo.uploadData(userProfileData) }
    }
}