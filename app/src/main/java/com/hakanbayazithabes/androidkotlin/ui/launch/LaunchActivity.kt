package com.hakanbayazithabes.androidkotlin.ui.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.ui.login.LoginActivity
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity

class LaunchActivity : AppCompatActivity() {
    lateinit var viewModel: LaunchActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this).get(LaunchActivityViewModel::class.java)



        viewModel.tokenCheck().observe(this) {
            var intent = when (it) {
                true -> {
                    Intent(this@LaunchActivity, UserActivity::class.java)
                }
                false -> {
                    Intent(this@LaunchActivity, LoginActivity::class.java)
                }
            }
            startActivity(intent)
            finish()
        }

    }
}