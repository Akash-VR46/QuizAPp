package com.example.quizapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quizapp.DataClass.History
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizHistoryDetailedViewFragment : Fragment() {
    private lateinit var historyDetailedView: View
    lateinit var score: TextView
    private lateinit var attemptedQuestion: TextView
    private lateinit var totalQuestion: TextView
    private lateinit var wrongQuestion: TextView
    private lateinit var percentage: TextView
    lateinit var historyData : List<History>
    private lateinit var quizDetailedViewHeading : TextView
    var rowNumber : Long = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        historyDetailedView = inflater.inflate(R.layout.score_detailed_view,container,false)
        rowNumber = requireArguments().getLong("RowClicked")
        historyData = listOf()
        score = historyDetailedView.findViewById(R.id.score)
        totalQuestion = historyDetailedView.findViewById(R.id.total_question)
        attemptedQuestion = historyDetailedView.findViewById(R.id.attempted_question)
        wrongQuestion = historyDetailedView.findViewById(R.id.wrong_question)
        percentage = historyDetailedView.findViewById(R.id.percentage)
        quizDetailedViewHeading = historyDetailedView.findViewById(R.id.quiz_history_detailed_view_heading)


        val activityContext = activity as LandingPageActivityController

        // setting the quiz detailed view data into ui using coroutines
        GlobalScope.launch {
            setQuizDetailedViewData(activityContext)
        }



        return historyDetailedView
    }

    /*
        fetching the detailed view of quiz from database and setting into UI
     */
    @SuppressLint("SetTextI18n")
    suspend fun setQuizDetailedViewData(context: LandingPageActivityController){

        historyData = context.database.historyDao().getDetailedScore(rowNumber)
        withContext(Dispatchers.Main) {

            quizDetailedViewHeading.text = "Quiz $rowNumber Detailed View"
            score.text = historyData[0].score.toString()
            totalQuestion.text = historyData[0].totalQuestion.toString()
            attemptedQuestion.text = historyData[0].attemptedQuestion.toString()
            percentage.text = ((historyData[0].score* 100 )/historyData[0].totalQuestion).toString() + " %"


        }




    }



}