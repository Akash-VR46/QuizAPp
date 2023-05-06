package com.example.quizapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.DataClass.Question
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionFragment: Fragment() {
    private lateinit var questionView: View
    private lateinit var questionData: List<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        questionView = inflater.inflate(R.layout.questions_layout, container, false)
        return questionView

    }
    /*
    Setting the views configurations
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activityContext =  activity as QuizActivityController
        activityContext.getDefaultViewContext().setViewIds(questionView)
        activityContext.getDefaultViewContext().setNavigationHandler(activityContext)

        GlobalScope.launch {

            sendQuestionsToView(activityContext)
        }

    }

    /*
    Fetching the questions from database and sending it to view(UI)
     */
    private suspend fun sendQuestionsToView(activityContext: QuizActivityController){
        questionData = activityContext.database.questionDao().getAllQuestion()

        withContext(Dispatchers.Main) {

            activityContext.getDefaultViewContext().getQuestionList(questionData)
            activityContext.getDefaultViewContext().config()

        }


    }
}