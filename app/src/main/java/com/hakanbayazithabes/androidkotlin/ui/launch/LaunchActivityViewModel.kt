package com.hakanbayazithabes.androidkotlin.ui.launch

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.ApiServices.TokenService
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.utility.HelperService
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchActivityViewModel() : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    var isSuccessful = MutableLiveData<Boolean>()

    private fun refreshTokenCheck() {
        CoroutineScope(Dispatchers.IO).launch {

            var token = HelperService.getTokenSharedPreferences()

            if (token != null) {

                var response = TokenService.refreshToken(token.refresh_token)

                Log.i("OkHttp", "Refresh Token istek yapıldı. sonuç=${response.isSuccessful}")

                if (response.isSuccessful) {
                    HelperService.saveTokenSharedPreferences(response.success!!)

                }

                isSuccessful.postValue(response.isSuccessful)

            } else {
                isSuccessful.postValue(false)

            }
            loadingState.postValue(LoadinState.Loaded)
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun tokenCheck() {
        loadingState.value = LoadinState.Loading
        var status = MutableLiveData<Boolean>()

        viewModelScope.launch {

            var response = TokenService.checkToken()
            status.value = response.isSuccessful


            if (response.isSuccessful) {
                loadingState.value = LoadinState.Loaded
                isSuccessful.value = true
            } else {
                errorState.value = response.fail
                refreshTokenCheck()
            }

        }
    }
}