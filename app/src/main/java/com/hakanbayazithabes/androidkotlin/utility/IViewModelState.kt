package com.hakanbayazithabes.androidkotlin.utility

import androidx.lifecycle.MutableLiveData
import com.hakanbayazithabes.androidkotlin.models.ApiError

interface IViewModelState {
    var loadingState : MutableLiveData<LoadinState>
    var errorState : MutableLiveData<ApiError>
}