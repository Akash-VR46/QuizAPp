package com.example.quizapp.DataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String? = null,
    val option4: String? = null,
    val answer: String
)
