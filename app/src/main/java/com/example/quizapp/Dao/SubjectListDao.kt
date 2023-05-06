package com.example.quizapp.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.DataClass.Question
import com.example.quizapp.DataClass.Subjects

@Dao
interface SubjectListDao {

    // Insert array:List of subjects
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data:ArrayList<Subjects>)
    // Fetch all the list of subjects from database
    @Query("SELECT * FROM subject")
    suspend fun getAllSubjects(): List<Subjects>

}