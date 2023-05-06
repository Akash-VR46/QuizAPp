package com.example.quizapp.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.DataClass.History

@Dao
interface HistoryDao {

    //Insert a new score into database
    @Insert
    suspend fun insertScore(history: History)

    // Retrieves the total number of scores stored in the database
    @Query("SELECT COUNT(id) FROM history")
    suspend fun getScore(): Long

    // Retrieves all the scores stored in the database
    @Query("SELECT * FROM history")
    suspend fun getAllScore(): List<History>

    // Retrieves the score(s) that match the given id
    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getDetailedScore(id:Long): List<History>
}