package com.hakanbayazithabes.androidkotlin.ui.signin

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.FragmentSigninBinding
import com.hakanbayazithabes.androidkotlin.models.UserSignIn
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity
import com.hakanbayazithabes.androidkotlin.utility.HelperService
import com.hakanbayazithabes.androidkotlin.utility.LoadinState

class SigninFragment : Fragment() {

    companion object {
        fun newInstance() = SigninFragment()
    }

    private lateinit var viewModel: SigninViewModel
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_signin, container, false)
        _binding = FragmentSigninBinding.bind(root)

        binding.btnSignin.setOnClickListener {
            val usersignIn = UserSignIn(
                binding.textSigninEmail.editText?.text.toString(),
                binding.textSigninPassword.editText?.text.toString()
            )

            viewModel.loadingState.observe(viewLifecycleOwner) {
                when (it) {
                    LoadinState.Loading -> binding.btnSignin.startAnimation()
                    LoadinState.Loaded -> binding.btnSignin.revertAnimation()
                }
            }

            viewModel.errorState.observe(viewLifecycleOwner) {
                HelperService.showErrorMessageByToast(it)
            }

            viewModel.signIn(usersignIn).observe(viewLifecycleOwner) {
                if (it) {
                    var intent = Intent(requireContext(), UserActivity::class.java)

                    startActivity(intent)
                    requireActivity().finish()
                }
            }

        }

        return root
    }


}