package com.hakanbayazithabes.androidkotlin.ui.launch

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.ApiServices.TokenService
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.launch

class LaunchActivityViewModel() : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()


    fun tokenCheck(): LiveData<Boolean> {
        loadingState.value = LoadinState.Loading

        var status = MutableLiveData<Boolean>()

        viewModelScope.launch {

            var response = TokenService.checkToken()

            status.value = response.isSuccessful

            loadingState.value = LoadinState.Loaded

            if (response.isSuccessful)
                errorState.value = response.fail!!

        }


        return status

    }
}