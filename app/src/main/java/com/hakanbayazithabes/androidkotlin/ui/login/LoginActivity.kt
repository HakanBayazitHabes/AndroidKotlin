package com.hakanbayazithabes.androidkotlin.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.ActivityLoginBinding
import com.hakanbayazithabes.androidkotlin.ui.signin.SigninFragment
import com.hakanbayazithabes.androidkotlin.ui.signup.SignupFragment

//import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var pagerAdapter = ScreenSlidePagerAdapter(this)
        pagerAdapter.addFragment(SigninFragment())
        pagerAdapter.addFragment(SignupFragment())

        binding.ViewPagerLogin.adapter = pagerAdapter
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        var fragments = ArrayList<Fragment>()

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments.get(position)
        }

        fun addFragment(f: Fragment) {
            fragments.add(f)
        }

    }
}