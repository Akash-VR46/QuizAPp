package com.example.quizapp.DataClass

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "subject")
data class Subjects(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val subject: String)




