package com.example.quizapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.DataClass.History
import com.example.quizapp.R
import com.example.quizapp.Fragments.ScoreHistoryFragment
import com.example.quizapp.HistoryClickListener

class ScoreHistoryAdapter(
    private val context: ScoreHistoryFragment,
    private val dataList: List<History>,
    private val itemListener: HistoryClickListener
) : RecyclerView.Adapter<ScoreHistoryAdapter.ScoreHistoryViewHolder>(){


    // This function creates a new ViewHolder instance and returns it.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreHistoryViewHolder {
        val itemView =LayoutInflater.from(parent.context)
            .inflate(R.layout.history_list_item,parent,false)
        return ScoreHistoryViewHolder(itemView)
    }

    //return the no. of item in the dataList
    override fun getItemCount(): Int {
        return dataList.size
    }

    // This function binds the data from the dataList to the views in the ViewHolder.
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ScoreHistoryViewHolder, position: Int) {
        val curr = dataList[position]

        holder.quizNumber.text = "Quiz ${curr.id}"

        // Set an onClickListener for the ViewHolder's itemView and call the quizHistoryClicked function in the itemListener.
        holder.itemView.setOnClickListener {
            itemListener.quizHistoryClicked(it,curr.id)
        }

    }

    // Defining a ViewHolder class that holds a reference to the views in the history_list_item.xml layout file.

    class ScoreHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        // Get a reference to the quiz_number TextView in the layout file.
        val quizNumber = itemView.findViewById<TextView>(R.id.quiz_number)

    }
}