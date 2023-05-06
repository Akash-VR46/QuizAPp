package com.example.quizapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.R

class SettingsFragment: Fragment() {
    private lateinit var settingFragmentView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        settingFragmentView = inflater.inflate(R.layout.quiz_settings_layout,container,false)
        return settingFragmentView


    }
    /*
        configuring the view of setting fragment
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activityContext = activity as LandingPageActivityController
        activityContext.landingPageView.setViewConfiguration(activityContext,settingFragmentView)
    }

}