package com.example.quizapp.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.DataClass.Question

@Dao
interface QuestionDao {

    //Insert all questions into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data:ArrayList<Question>)

    //Retrive list of question from database
    @Query("SELECT * FROM question")
    fun getAllQuestion():List<Question>

    //Retrive list of question where option3 is not null
    @Query("SELECT * FROM question WHERE option3 IS NOT NULL")
    fun getAllMCQQuestion():List<Question>

    //Retrive list of question where option3 is null
    @Query("SELECT * FROM question WHERE option3 IS NULL")
    fun getAllTrueFalseQuestion():List<Question>
}