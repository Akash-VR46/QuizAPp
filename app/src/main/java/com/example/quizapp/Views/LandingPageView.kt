package com.example.quizapp.Views

import android.content.Context

import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.Fragments.SubjectChoiceFragment
import com.example.quizapp.QuizType
import com.example.quizapp.R
import com.example.quizapp.Activities.LandingPageActivityController

class LandingPageView  {

    private lateinit var radioGroup: RadioGroup
    private lateinit var multipleChoiceRadioButton: RadioButton
    private lateinit var trueAndFalseRadioButton: RadioButton
    private lateinit var subjectChoiceButton: Button


    /*
    Initialize the UI Elements
     */
    private fun setViewsElement(view : View){

        radioGroup = view.findViewById(R.id.landing_page_radio_group)
        multipleChoiceRadioButton = view.findViewById(R.id.multiple_choice_radio_button)
        trueAndFalseRadioButton = view.findViewById(R.id.true_false_radio_button)
        subjectChoiceButton = view.findViewById(R.id.start_quiz)
    }


    /*
        Sets the click listener and handler for the Ui elements
        configuring the quiz type(MCQ/T&F) based on user choice.
     */
    private fun setHandler(context: LandingPageActivityController){
        //sets the quiz type based on user selection
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if(checkedId == multipleChoiceRadioButton.id){
                QuizType.setQuizType("MultipleChoiceQuestion")
            }
            else if(checkedId == trueAndFalseRadioButton.id){
                QuizType.setQuizType("TrueAndFalse")
            }
        }

        //  After selection of quiz type user will be send to select subject

        subjectChoiceButton.setOnClickListener{

            val subjectFragment = SubjectChoiceFragment()
            val subjectFragmentTransition: FragmentTransaction = context.supportFragmentManager.beginTransaction()
            subjectFragmentTransition.replace(R.id.flContent,subjectFragment).commit()
        }

    }
    fun setViewConfiguration(context:Context ,settingsView: View){
        val ctxt = context as LandingPageActivityController
        setViewsElement(settingsView)
        setHandler(ctxt)

    }



}