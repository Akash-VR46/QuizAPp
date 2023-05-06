package com.example.quizapp

import android.view.View


interface SubjectClickListener {
    fun recycleViewSubjectClicked(v : View?, subject: String)
}