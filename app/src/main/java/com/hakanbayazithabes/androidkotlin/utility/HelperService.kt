package com.hakanbayazithabes.androidkotlin.utility

import android.content.Context
import com.google.gson.Gson
import com.hakanbayazithabes.androidkotlin.models.TokenAPI

class HelperService {
    companion object {
        fun saveTokenSharedPreferences(token: TokenAPI) {
            var preference =
                GlobalApp.getContext().getSharedPreferences("token", Context.MODE_PRIVATE)

            var editor = preference.edit()

            editor.putString("token", Gson().toJson(token))

            editor.apply()


        }
    }
}