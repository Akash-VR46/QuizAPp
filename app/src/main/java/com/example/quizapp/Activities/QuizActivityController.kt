package com.example.quizapp.Activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.*
import com.example.quizapp.Database.QuizDatabase
import com.example.quizapp.Fragments.QuestionFragment
import com.example.quizapp.Fragments.NavigationFragment
import com.example.quizapp.DataClass.History
import com.example.quizapp.DataClass.Question
import com.example.quizapp.Views.QuizView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizActivityController : AppCompatActivity() {

    // Initializing variables
    private lateinit var quizView: QuizView
    private lateinit var quizModel: QuestionModel

    lateinit var database: QuizDatabase
    lateinit var lst:ArrayList<Question>
    lateinit var quizType : QuizType
    lateinit var history : History
    lateinit var sharedPreferences:SharedPreferences
    lateinit var quizState: QuizState

    var isQuizCompleted = false

    var selectedQuizType: String = ""

    // Function to return the default QuizView context
    fun getDefaultViewContext(): QuizView {
        return quizView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.quiz_activity_layout)

        /*
           If any previous quiz state present then start with that quiz only.
         */
        sharedPreferences = getSharedPreferences("previousQuizState", MODE_PRIVATE)

        quizType = QuizType.getInstance()
        selectedQuizType = QuizType.getQuizType()
        database = QuizDatabase.getDataBase(this)
        lst = ArrayList()
        quizModel = QuestionModel()
        quizModel.getDataFromJson(this)

        //Inserting Questions into database
        GlobalScope.launch {
            database.questionDao().insertAll(lst)
        }

        // Replacing the NavigationFragment into the container
        val secondFragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        secondFragmentTransaction.replace(R.id.quiz_navigation_layout, NavigationFragment())
        secondFragmentTransaction.commit()

        // Initializing QuizView object and retrieving the quiz state if it was previously saved
        quizView = QuizView()
        QuizType.getAttemptedQuestionArray()?.let {
            quizView.retrieveDataFromState(
                QuizType.getScore(), QuizType.getCurrentQuestion(),
                it
            )
        }

        //Replacing the question fragment into the container
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.quiz_view_layout, QuestionFragment())
        fragmentTransaction.commit()



    }

    /*
        Saving the current state of the quiz.
     */
    override fun onPause() {
        super.onPause()

        // Saving quiz state into the SharedPreferences
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        quizState = QuizState()
        if(!isQuizCompleted){
            quizState.quizType = QuizType.getQuizType()
            quizState.subject = QuizType.getSubject()
            quizState.attemptedQuestionArray = quizView.answerAttemptedArray
            quizState.score = quizView.score
            quizState.currentQuestion = quizView.currentQuestion
        }

        val json = gson.toJson(quizState)
        Log.d("InsideQuiz",json.toString())
        editor.putString("previousQuizState", json)
        editor.apply()
    }


}


