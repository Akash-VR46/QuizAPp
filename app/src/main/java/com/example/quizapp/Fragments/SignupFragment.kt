package com.example.quizapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.R

class SignupFragment : Fragment() {
    lateinit var signupFragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signupFragmentView = inflater.inflate(R.layout.signup_layout,container,false)
        return signupFragmentView
    }
}