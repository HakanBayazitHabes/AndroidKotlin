package com.hakanbayazithabes.androidkotlin.ui.signup

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.viewpager2.widget.ViewPager2
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.ActivityLoginBinding
import com.hakanbayazithabes.androidkotlin.databinding.FragmentSigupBinding
import com.hakanbayazithabes.androidkotlin.models.UserSignUp

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var viewModel: SignupViewModel
    private var _binding: FragmentSigupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        var view = inflater.inflate(R.layout.fragment_sigup, container, false)
        var LogindActivity = inflater.inflate(R.layout.activity_login, container, false)
        _binding = FragmentSigupBinding.bind(view)
        var viewPagerLoginBinding = ActivityLoginBinding.bind(LogindActivity)
        var viewPagerLogin = viewPagerLoginBinding.ViewPagerLogin


        binding.btnSignin.setOnClickListener {
            var userSignUp = UserSignUp(
                binding.textSignupUsername.editText?.text.toString(),
                binding.textSignupEmail.editText?.text.toString(),
                binding.textSignupPassword.editText?.text.toString(),
                binding.textSignupCity.editText?.text.toString()
            )

            viewModel.signUp(userSignUp).observe(viewLifecycleOwner) {
                if (it) {
                    viewPagerLogin.currentItem = 0
                } else {
                    //hata var
                }
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}