package com.example.quizapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.Dao.HistoryDao
import com.example.quizapp.Dao.QuestionDao
import com.example.quizapp.Dao.SubjectListDao
import com.example.quizapp.DataClass.History
import com.example.quizapp.DataClass.Question
import com.example.quizapp.DataClass.Subjects

@Database(entities = [History::class, Question::class, Subjects::class], version = 1)
abstract class QuizDatabase: RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun historyDao(): HistoryDao
    abstract fun subjectDao(): SubjectListDao

    /*
    Creating singleton object of our database which will be used through out the whole application
    @volatile->used for making threadsafe
     */

    companion object{
        @Volatile
        private var INSTANCE: QuizDatabase? =null

        fun getDataBase(context:Context): QuizDatabase {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        QuizDatabase::class.java,"Question1").build()
                }

            }
            return  INSTANCE!!

        }
    }
}