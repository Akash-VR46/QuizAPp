package com.example.quizapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.Fragments.LoginFragment
import com.example.quizapp.Fragments.SubjectChoiceFragment
import com.example.quizapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup_activity_layout)

        val loginFragment = LoginFragment()
        val loginFragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
        loginFragmentTransition.replace(R.id.login_sing_up_frame_layout,loginFragment).commit()
    }
}