package com.example.quizapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.*
import com.example.quizapp.Adapters.SubjectListAdapter
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.Activities.QuizActivityController
import com.example.quizapp.DataClass.Subjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubjectChoiceFragment : Fragment(),SubjectClickListener {
    private lateinit var subjectChoiceRecyclerView: RecyclerView
    private lateinit var listOfSubjects: List<Subjects>
    private lateinit var subjectChoiceFragmentView: View
    private lateinit var startButton: Button
    lateinit var activityContext : LandingPageActivityController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listOfSubjects = listOf()

        subjectChoiceFragmentView= inflater.inflate(R.layout.list_of_subjects_layout,container,false)
        subjectChoiceRecyclerView = subjectChoiceFragmentView.findViewById(R.id.recyclerView)
        startButton = subjectChoiceFragmentView.findViewById(R.id.start_quiz)

        //  setting layout manager for recycler view
        subjectChoiceRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return subjectChoiceFragmentView

    }
    /*
    Getting context of parent activity after activity created
    and setting data to UI from room dataBase.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activityContext = activity as LandingPageActivityController
        startButton.setOnClickListener {
            val intent = Intent(activityContext, QuizActivityController::class.java)
            activityContext.startActivity(intent)
        }

        // setting the subject data into ui using coroutines
        GlobalScope.launch {
            setSubjectData(activityContext)
        }
    }

    /*
    fetching list of subjects from room database
    and setting into UI
     */
    private suspend fun setSubjectData(activityContext: LandingPageActivityController){
        listOfSubjects = activityContext.database.subjectDao().getAllSubjects()
        withContext(Dispatchers.Main) {
            val adapter = SubjectListAdapter(this@SubjectChoiceFragment, listOfSubjects,this@SubjectChoiceFragment)
            subjectChoiceRecyclerView.adapter = adapter
        }
    }

    /*
        enabling start button after choosing the subject by the user
     */
    override fun recycleViewSubjectClicked(v: View?, subject: String) {
        startButton.isEnabled = true
        activityContext.quizType.setSubject(subject)
    }

}