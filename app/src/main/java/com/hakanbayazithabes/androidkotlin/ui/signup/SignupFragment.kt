package com.hakanbayazithabes.androidkotlin.ui.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputLayout
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.ActivityLoginBinding
import com.hakanbayazithabes.androidkotlin.databinding.FragmentSigupBinding
import com.hakanbayazithabes.androidkotlin.models.UserSignUp
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        var fragmentView = inflater.inflate(R.layout.fragment_sigup, container, false)
        var viewPagerLogin = requireActivity().findViewById<ViewPager2>(R.id.ViewPagerLogin)
        _binding = FragmentSigupBinding.bind(fragmentView)


        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                LoadinState.Loading -> binding.btnSignup.startAnimation()
                LoadinState.Loaded -> binding.btnSignup.revertAnimation()
            }
        }

        binding.btnSignup.setOnClickListener {

            var userSignUp = UserSignUp(
                binding.textSignupUsername.editText?.text.toString(),
                binding.textSignupEmail.editText?.text.toString(),
                binding.textSignupPassword.editText?.text.toString(),
                binding.textSignupCity.editText?.text.toString()
            )

            viewModel.signUp(userSignUp).observe(viewLifecycleOwner) {

                if (it) {

                    viewPagerLogin.currentItem = 0

                    CoroutineScope(Dispatchers.Main).launch {

                        delay(1000)
                        onAlertDailog(fragmentView)
                    }
                } else {
                    //hata var
                }
            }
        }
        return fragmentView
    }
    private fun onAlertDailog(view: View) {
        var builder = AlertDialog.Builder(view.context)

        builder.setMessage("Bilgileriniz başarıyla kaydedilmiştir.Email ve şifreniz ile giriş yapabilirsiniz")

        builder.setPositiveButton("Tamam") { _, _ -> }

        builder.show()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}