package com.example.quizapp.Views

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.Fragments.QuizCompletionFragment
import com.example.quizapp.QuizType
import com.example.quizapp.R
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.DataClass.Question


class QuizView {

    // Declaring private variables
    private lateinit var questionDisplay: TextView
    private lateinit var nextButton: Button
    private lateinit var optionGroup: RadioGroup
    private lateinit var option1Button: RadioButton
    private lateinit var option2Button: RadioButton
    private lateinit var option3Button: RadioButton
    private lateinit var option4Button: RadioButton
    lateinit var questionList: List<Question>
    private lateinit var prevButton: Button
    lateinit var answerAttemptedArray: BooleanArray
    private lateinit var subjectHeadingDisplay : TextView

    var score = 0
    var currentQuestion = 0


    /*
        retrieving previous quiz state from shared preference
     */
    fun retrieveDataFromState(score:Int, currentQuestion: Int,answerAttemptedArray: BooleanArray){
        Log.d("Came","To set Data")
        this.score = score
        this.currentQuestion = currentQuestion
        this.answerAttemptedArray = answerAttemptedArray

    }
    /*
        checking the selected option is correct or not
     */
    private fun isCorrect(correctAnswer: String, answerChoose:String):Boolean{
        if(correctAnswer == answerChoose){
            return true
        }
        return false
    }

    /*
    returning the id of the selected radio button
     */
    private fun getSelectedRadioButtonId(): Int {
        return optionGroup.checkedRadioButtonId
    }

    /*
    configuring the previous and next button handlers
     */
    fun setNavigationHandler(context:Context){
        val ctxt = context as QuizActivityController
        subjectHeadingDisplay.text = QuizType.getSubject()


        nextButton.setOnClickListener {

            onNextButtonClick(context)
        }

        prevButton.setOnClickListener {

            onPrevButtonClick(context)
        }


    }
    /*
        setting the question list and attempted array.
     */
    fun getQuestionList(questions: List<Question>){
        questionList = questions
        answerAttemptedArray = BooleanArray(questionList.size)
    }
    /*
    function which check if next question is available or not
    if not available then submitting the quiz
     */
    private fun nextQuestion(currentQuestion:Int, totalQuestions:Int, Score:Int,context: Context){
        if(currentQuestion<totalQuestions){
            setQuestion(questionList[currentQuestion].question)
            setOptions(questionList[currentQuestion])

        }
        else{
            val ctxt = context as QuizActivityController
            val fragmentManager = ctxt.supportFragmentManager
            val fragment = fragmentManager.findFragmentById(R.id.quiz_navigation_layout)
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit()
            }

            val firstFragmentTransaction: FragmentTransaction = context.supportFragmentManager.beginTransaction()
            firstFragmentTransaction.replace(R.id.quiz_view_layout, QuizCompletionFragment())
            firstFragmentTransaction.commit()
        }
    }
    /*
    Initializing the UI
     */
    fun setViewIds(view: View){
        subjectHeadingDisplay = view.findViewById(R.id.subject_heading)

        questionDisplay = view.findViewById(R.id.tv_question)
        optionGroup = view.findViewById(R.id.multiple_choice_radio_group)
        option1Button = view.findViewById(R.id.option1)
        option2Button = view.findViewById(R.id.option2)
        option3Button = view.findViewById(R.id.option3)
        option4Button = view.findViewById(R.id.option4)


    }
    fun setNavigationViewIds(view:View){
        nextButton = view.findViewById(R.id.next)
        prevButton = view.findViewById(R.id.prev)

    }

    @SuppressLint("SetTextI18n")
    /*
    setting the questions in UI
     */
    private fun setQuestion(question:String){
        if(currentQuestion == 0){
            disablePrev()
        }
        else{
            enablePrev()
        }
        if(currentQuestion == questionList.size - 1){
            nextButton.text = "SUBMIT"
        }
        else{
            nextButton.text = "Next"
        }

        questionDisplay.text = "Q${currentQuestion+1}" +". " +question
    }
    // setting the options and disabling the options according the question type
    private fun setOptions(question: Question){
        if(answerAttemptedArray[currentQuestion]){
            disableAllOptions()
        }
        else {
            enableAllOptions()

        }
        option1Button.text = question.option1
        option2Button.text = question.option2

        if (question.option3 != null && question.option4 != null) {
            makeOptionsVisible()
            option3Button.text = question.option3
            option4Button.text = question.option4
        } else {
            disableOptions()
        }

    }

    // disabling previous button
    private fun disablePrev(){
        prevButton.isEnabled = false
    }
    // enabling previous button
    private fun enablePrev(){
        prevButton.isEnabled = true
    }

    // disabling options 3 and 4
    private fun disableOptions(){
        option3Button.isVisible = false
        option4Button.isVisible = false
    }
    // disabling all the options
    private fun disableAllOptions(){
        option1Button.isEnabled = false
        option2Button.isEnabled = false
        option3Button.isEnabled = false
        option4Button.isEnabled = false
    }

    //enabling all the options
    private fun enableAllOptions(){

        option1Button.isEnabled = true
        option2Button.isEnabled = true
        option3Button.isEnabled = true
        option4Button.isEnabled = true
    }

    private fun makeOptionsVisible(){
        option3Button.isVisible = true
        option4Button.isVisible = true
    }

    /*
    on next button click checks for option selected is correct or not
    increase the score
    sets the question number attempted and navigate to next question
     */
    private fun onNextButtonClick(context:Context){

        val selectedOptionId = getSelectedRadioButtonId()
        optionGroup.clearCheck()

        if(selectedOptionId != -1 && !answerAttemptedArray[currentQuestion]) {
            val answerOpted = when(selectedOptionId){
                R.id.option1 -> option1Button.text.toString()
                R.id.option2 -> option2Button.text.toString()
                R.id.option3 -> option3Button.text.toString()
                R.id.option4 -> option4Button.text.toString()
                else -> ""
            }
            if(answerOpted != ""){
                answerAttemptedArray[currentQuestion] = true
                if(isCorrect(questionList[currentQuestion++].answer,answerOpted)){
                    score++
                }
            }
            else{
                currentQuestion++
            }
        }
        else{
            currentQuestion++
        }
        nextQuestion(currentQuestion,questionList.size, score,context)
    }
    /*
    on prev button click checks for option selected is correct or not
    increase the score
    sets the question number attempted and navigate to previous question
     */
    private fun onPrevButtonClick(context:Context){
        val selectedOptionId = getSelectedRadioButtonId()
        optionGroup.clearCheck()
        if(selectedOptionId != -1 && !answerAttemptedArray[currentQuestion]) {
            val answerOpted = when(selectedOptionId){
                R.id.option1 -> option1Button.text.toString()
                R.id.option2 -> option2Button.text.toString()
                R.id.option3 -> option3Button.text.toString()
                R.id.option4 -> option4Button.text.toString()
                else -> ""
            }
            if(answerOpted != ""){
                answerAttemptedArray[currentQuestion] = true
                if(isCorrect(questionList[currentQuestion--].answer,answerOpted)){
                    score++
                }
            }
            else{
                currentQuestion--
            }

        }
        else{
            currentQuestion--
        }
        nextQuestion(currentQuestion,questionList.size, score,context)
    }
    // configuring questions and options
    fun config(){
        setQuestion(questionList[currentQuestion].question)
        setOptions(questionList[currentQuestion])
    }

}



