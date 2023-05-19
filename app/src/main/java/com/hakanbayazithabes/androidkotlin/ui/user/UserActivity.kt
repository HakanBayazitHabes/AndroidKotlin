package com.hakanbayazithabes.androidkotlin.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.utility.HelperService
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState

class UserActivity : AppCompatActivity() {

    companion object {
        lateinit var loadingView: View

        fun setLoadingStatus(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.loadingState.observe(viewLifecycleOwner) {
                when (it) {
                    LoadinState.Loading -> loadingView.visibility = View.VISIBLE
                    LoadinState.Loaded -> loadingView.visibility = View.GONE
                }
            }
        }

        fun setErrorStatus(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.errorState.observe(viewLifecycleOwner) {
                HelperService.showErrorMessageByToast(it)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        loadingView = findViewById(R.id.full_page_loading_view)

    }
}