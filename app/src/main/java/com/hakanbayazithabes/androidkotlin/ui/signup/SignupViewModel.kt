package com.hakanbayazithabes.androidkotlin.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.ApiServices.LoginService
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.models.UserSignUp
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    fun signUp(userSignUp: UserSignUp): LiveData<Boolean> {

        var status = MutableLiveData<Boolean>()

        loadingState.value = LoadinState.Loading

        viewModelScope.launch {

            var response = LoginService.signUp(userSignUp)

            status.value = response.isSuccessful

            loadingState.value = LoadinState.Loaded

            if (!response.isSuccessful)
                errorState.value = response.fail!!

        }
        return status
    }
}