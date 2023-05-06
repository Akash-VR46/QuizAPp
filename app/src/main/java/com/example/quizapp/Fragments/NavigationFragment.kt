package com.example.quizapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.R

class NavigationFragment : Fragment() {
    private lateinit var navigationQuestionView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navigationQuestionView = inflater.inflate(R.layout.quiz_navigation_layout, container, false)
        return navigationQuestionView
    }
    /*
    configuring the quiz question navigation buttons
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activityContext =  activity as QuizActivityController
        activityContext.getDefaultViewContext().setNavigationViewIds(navigationQuestionView)

    }
}