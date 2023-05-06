package com.example.quizapp

import com.google.gson.annotations.SerializedName


// singleton class to save the state through out the whole quiz
object QuizType {

    private var quizType : String = "Default"

    private var subject : String? = null

    private var instance: QuizType? = null

    private var currentQuestion = 0

    private var attemptedQuestionArray : BooleanArray? = null

    private var score  = 0

    fun getInstance(): QuizType {
        if (instance == null) {
            instance = QuizType
        }
        return instance as QuizType
    }
    fun setQuizType(type: String){
        quizType = type

    }
    fun getQuizType(): String {
        return quizType

    }
    fun setSubject(type: String){
        subject = type

    }
    fun getSubject(): String? {
        return subject

    }

    fun getScore():Int{
        return score
    }
    fun setScore(value: Int){
        score = value
    }

    fun getCurrentQuestion():Int{
        return currentQuestion
    }
    fun setCurrentQuestion(value: Int){
        currentQuestion = value
    }


    fun getAttemptedQuestionArray(): BooleanArray? {
        return attemptedQuestionArray
    }
    fun setAttemptedQuestionArray(value: BooleanArray?){
        attemptedQuestionArray = value
    }


}
