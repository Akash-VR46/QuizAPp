package com.example.quizapp



/*
class to save the state of quiz
 */
class QuizState {
    var score: Int = 0
    var currentQuestion:Int = 0
    var subject: String? = null
    var attemptedQuestionArray : BooleanArray? = null
    var quizType: String = "Default"

}