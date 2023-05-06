package com.example.quizapp

import android.view.View

interface HistoryClickListener {
    fun quizHistoryClicked(v : View?, id: Long){}
}