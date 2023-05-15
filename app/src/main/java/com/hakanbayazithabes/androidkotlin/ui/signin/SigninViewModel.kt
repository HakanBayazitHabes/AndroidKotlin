package com.hakanbayazithabes.androidkotlin.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.ApiServices.LoginService
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.models.UserSignIn
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.launch

class SigninViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    fun signIn(userSignIn: UserSignIn): LiveData<Boolean> {
        var status = MutableLiveData<Boolean>()
        loadingState.value = LoadinState.Loading

        viewModelScope.launch {
            var apiResponse = LoginService.signIn(userSignIn)

            if (!apiResponse.isSuccessful) {
                status.value = apiResponse.isSuccessful
                errorState.value = apiResponse.fail!!
            } else {
                status.value = apiResponse.isSuccessful
            }
            loadingState.value = LoadinState.Loaded
        }
        return status

    }
}