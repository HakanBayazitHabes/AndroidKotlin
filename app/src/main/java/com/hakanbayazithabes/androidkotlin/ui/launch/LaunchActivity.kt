package com.hakanbayazithabes.androidkotlin.ui.launch

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.ActivityLaunchBinding
import com.hakanbayazithabes.androidkotlin.ui.login.LoginActivity
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity
import com.hakanbayazithabes.androidkotlin.utility.LoadinState

class LaunchActivity : AppCompatActivity() {
    lateinit var viewModel: LaunchActivityViewModel
    private lateinit var binding: ActivityLaunchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this).get(LaunchActivityViewModel::class.java)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f)
        var scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f)

        var animator = ObjectAnimator.ofPropertyValuesHolder(
            binding.imgComponyLogo,
            scaleX,
            scaleY
        )

        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = Animation.INFINITE
        animator.duration = 1000

        viewModel.loadingState.observe(this) {
            when (it) {
                LoadinState.Loading -> animator.start()
                LoadinState.Loaded -> animator.cancel()
            }
        }

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