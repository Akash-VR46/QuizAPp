package com.example.quizapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.quizapp.*
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.DataClass.History
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizCompletionFragment : Fragment() {
    private lateinit var quizCompletionFragmentView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizCompletionFragmentView = inflater.inflate(R.layout.quiz_completion_layout,container,false)

        return quizCompletionFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activityContext = activity as QuizActivityController
        val backToHomeButton = quizCompletionFragmentView.findViewById<Button>(R.id.back_to_home)
        backToHomeButton.setOnClickListener {
            val intent = Intent(activityContext, LandingPageActivityController::class.java)
            startActivity(intent)
        }

        // sending the score to the database using coroutines
        GlobalScope.launch {

            sendHistoryToDB(activityContext)
        }
        activityContext.isQuizCompleted = true




    }

    /*
    After completion of quiz sending results to database.
     */
    private suspend fun sendHistoryToDB(activityContext: QuizActivityController){
        val historyCount = activityContext.database.historyDao().getScore()
        val viewContext = activityContext.getDefaultViewContext()
        activityContext.database.historyDao().insertScore(
            History(historyCount+1,
            viewContext.answerAttemptedArray.count{it},
            viewContext.questionList.size,
            activityContext.quizType.getSubject()!!,
            viewContext.score,
            activityContext.quizType.getQuizType())
        )





    }
}