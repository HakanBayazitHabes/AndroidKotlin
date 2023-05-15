package com.hakanbayazithabes.androidkotlin.utility

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.hakanbayazithabes.androidkotlin.Exceptions.OfflineException
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.TokenAPI
import retrofit2.Response

class HelperService {
    companion object {

        fun <T> handleException(ex: Exception): ApiResponse<T> {
            return when (ex) {
                is OfflineException -> {
                    val exMessage =
                        arrayListOf(GlobalApp.getContext().resources.getString(R.string.ex_no_exception))
                    var apiError = ApiError(exMessage, 500, true)
                    ApiResponse(false, fail = apiError)
                }
                else -> {
                    val exMessage =
                        arrayListOf(GlobalApp.getContext().resources.getString(R.string.ex_comman_error))
                    var apiError = ApiError(exMessage, 500, true)

                    ApiResponse(false, fail = apiError)
                }
            }
        }

        fun saveTokenSharedPreferences(token: TokenAPI) {
            var preference =
                GlobalApp.getContext().getSharedPreferences("token", Context.MODE_PRIVATE)

            var editor = preference.edit()

            editor.putString("token", Gson().toJson(token))

            editor.apply()
        }

        fun <T1, T2> handlerApiError(response: Response<T1>): ApiResponse<T2> {
            var apiError: ApiError? = null

            if (response.errorBody() != null) {
                var errorBody = response.errorBody()!!.string()

                apiError = Gson().fromJson(errorBody, ApiError::class.java)
            }

            return ApiResponse(false, null, apiError)
        }

        fun showErrorMessageByToast(apiError: ApiError?) {
            if (apiError == null) return
            var errorBuilder = StringBuilder()

            if (apiError.IsShow) {
                for (error in apiError.Errors) {
                    errorBuilder.append(error + "\n")
                }
            }
            Toast.makeText( GlobalApp.getContext() , errorBuilder.toString(), Toast.LENGTH_LONG)
                .show()
        }

    }
}