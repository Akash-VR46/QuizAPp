package com.example.quizapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.Activities.LoginActivity
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.R

class LoginFragment : Fragment() {

    lateinit var loginFragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginFragmentView = inflater.inflate(R.layout.activity_login,container,false)

        val userEmail = loginFragmentView.findViewById<EditText>(R.id.email)
        val userPassword = loginFragmentView.findViewById<EditText>(R.id.password)

        val signupText = loginFragmentView.findViewById<TextView>(R.id.login_to_signup)

        signupText.setOnClickListener {

            val signupFragment = SignupFragment()
            val signupFragmentTransition: FragmentTransaction = parentFragmentManager.beginTransaction()
            signupFragmentTransition.replace(R.id.login_sing_up_frame_layout,signupFragment).commit()

        }

//                    val username = userEmail.text.toString()
//            val password = userPassword.text.toString()
//
//            if (username.isEmpty() || password.isEmpty()) {
//                if (username.isEmpty()) {
//                    userEmail.error = "Please enter your user name"
//
//                }
//                if (password.isEmpty()) {
//                    userPassword.error = "Please enter password"
//                }
//
//                Toast.makeText( context,"Enter valid details", Toast.LENGTH_SHORT).show()
//
//            }
//            else if(!username.matches(emailPattern.toRegex())){
//                userEmail.error="Enter valid email!"
//                Toast.makeText(context, "Please Enter valid email!",Toast.LENGTH_SHORT).show()
//            }
//
//            else if(password.length < 6){
//                userPassword.error = "Enter password more than 6 character"
//                Toast.makeText(context,"Enter password more than 6 character",Toast.LENGTH_SHORT).show()
//            }
//            else {
//                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        val subjectListFragment = SubjectListFragment()
//                        replaceFragments(subjectListFragment)
//                    } else {
//                        Toast.makeText(context, "Try Again..", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }




        return loginFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = activity as LoginActivity
        val loginButton = loginFragmentView.findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val intent = Intent(context, LandingPageActivityController::class.java)
            context.startActivity(intent)
        }



    }
}