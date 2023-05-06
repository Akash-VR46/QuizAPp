package com.example.quizapp.DataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    var attemptedQuestion:Int,
    var totalQuestion:Int,
    var subject: String,
    var score: Int,
    var quizType: String
)
