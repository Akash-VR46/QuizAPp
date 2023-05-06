package com.example.quizapp

import android.content.Context
import android.content.res.AssetManager
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.DataClass.Question
import com.example.quizapp.DataClass.Subjects
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser


class QuestionModel{

    /*
    setting list of subjects for quiz
     */
    fun getListOfSubjects(context: Context){
        val activityContext = context as LandingPageActivityController
        activityContext.subjectList.addAll(listOf(
            Subjects(1,"Maths"),
            Subjects(2,"Chemistry"),
            Subjects(3,"Physics"),
            Subjects(4,"Biology"),
            Subjects(5,"Social"),
            Subjects(6,"Science"),
            Subjects(7,"C"),
            Subjects(8,"C++"),
            Subjects(9,"Kotlin"),
            Subjects(10,"Android"),
            Subjects(11,"Java")

        )
        )

    }
    /*
        fetching json data from assets and sending into room
     */
    fun getDataFromJson(context:Context) {
        val activityContext = context as QuizActivityController

        val jsonString = activityContext.assets.readFile("dataa.json")
        val jsonParser: JsonParser = JsonParser()
        val jsonElement: JsonElement = jsonParser.parse(jsonString)

        val jsonObj: JsonObject = jsonElement.asJsonObject


        val jsonArray =
            jsonObj[activityContext.quizType.getQuizType()].asJsonObject[activityContext.quizType.getSubject()].asJsonArray

        for (i in 0 until jsonArray.size()) {
            if (activityContext.quizType.getQuizType() == "TrueAndFalse") {
                activityContext.lst.add(
                    Question(
                        id = i + 1,
                        question = jsonArray[i].asJsonObject["question"].toString()
                            .replace("\"", ""),
                        option1 = jsonArray[i].asJsonObject["option1"].toString().replace("\"", ""),
                        option2 = jsonArray[i].asJsonObject["option2"].toString().replace("\"", ""),
                        answer = jsonArray[i].asJsonObject["answer"].toString().replace("\"", "")
                    )
                )
            } else if (activityContext.quizType.getQuizType() == "MultipleChoiceQuestion") {
                activityContext.lst.add(
                    Question(
                        id = i + 1,
                        question = jsonArray[i].asJsonObject["question"].toString()
                            .replace("\"", ""),
                        option1 = jsonArray[i].asJsonObject["option1"].toString().replace("\"", ""),
                        option2 = jsonArray[i].asJsonObject["option2"].toString().replace("\"", ""),
                        option3 = jsonArray[i].asJsonObject["option3"].toString().replace("\"", ""),
                        option4 = jsonArray[i].asJsonObject["option4"].toString().replace("\"", ""),
                        answer = jsonArray[i].asJsonObject["answer"].toString().replace("\"", "")
                    )
                )
            } else {

                if (jsonArray[i].asJsonObject.size() > 4) {
                    activityContext.lst.add(
                        Question(
                            id = i + 1,
                            question = jsonArray[i].asJsonObject["question"].toString()
                                .replace("\"", ""),
                            option1 = jsonArray[i].asJsonObject["option1"].toString()
                                .replace("\"", ""),
                            option2 = jsonArray[i].asJsonObject["option2"].toString()
                                .replace("\"", ""),
                            option3 = jsonArray[i].asJsonObject["option3"].toString()
                                .replace("\"", ""),
                            option4 = jsonArray[i].asJsonObject["option4"].toString()
                                .replace("\"", ""),
                            answer = jsonArray[i].asJsonObject["answer"].toString()
                                .replace("\"", "")
                        )
                    )
                } else {

                    activityContext.lst.add(
                        Question(
                            id = i + 1,
                            question = jsonArray[i].asJsonObject["question"].toString()
                                .replace("\"", ""),
                            option1 = jsonArray[i].asJsonObject["option1"].toString()
                                .replace("\"", ""),
                            option2 = jsonArray[i].asJsonObject["option2"].toString()
                                .replace("\"", ""),
                            answer = jsonArray[i].asJsonObject["answer"].toString()
                                .replace("\"", "")
                        )
                    )


                }
            }
        }
    }


    fun AssetManager.readFile(fileName:String) = open(fileName).bufferedReader().use { it.readText() }
}






