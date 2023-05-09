package com.hakanbayazithabes.androidkotlin.utility

import android.app.Application
import android.content.Context

class GlobalApp : Application() {
    companion object {
        private lateinit var myContext: Context

        public fun getContext(): Context {
            return myContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        myContext = this
    }
}