package com.example.quizapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.*
import com.example.quizapp.Adapters.ScoreHistoryAdapter
import com.example.quizapp.Activities.LandingPageActivityController
import com.example.quizapp.DataClass.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreHistoryFragment : Fragment(),HistoryClickListener {

    private lateinit var scoreHistoryFragmentView: View
    private lateinit var scoreHistoryRecyclerView: RecyclerView
    private lateinit var historyData: List<History>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        historyData = listOf()
        scoreHistoryFragmentView = inflater.inflate(R.layout.score_history_fragment,container,false)

        scoreHistoryRecyclerView = scoreHistoryFragmentView.findViewById(R.id.score_history_recycler_view)
        scoreHistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())



        return scoreHistoryFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activityContext = activity as LandingPageActivityController
        GlobalScope.launch {
            setData(activityContext)
        }

    }

    /*
        Fetching all the quiz history from the database and setting into UI in recycler view.
     */
    private suspend fun setData(activityContext : LandingPageActivityController) {
        historyData = activityContext.database.historyDao().getAllScore()
        withContext(Dispatchers.Main) {
            val adapter = ScoreHistoryAdapter(this@ScoreHistoryFragment, historyData,this@ScoreHistoryFragment)
            scoreHistoryRecyclerView.adapter = adapter
        }

    }


    /*
    Function shows a fragment which shows detailed view of quiz history selected by user
     */
    override fun quizHistoryClicked(v: View?, id: Long) {
        val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        val quizDetailedFragment = QuizHistoryDetailedViewFragment()
        val args = Bundle()
        args.putLong("RowClicked",id)
        quizDetailedFragment.arguments = args
        fragmentTransaction.replace(R.id.flContent,quizDetailedFragment)
        fragmentTransaction.commit()

    }


}