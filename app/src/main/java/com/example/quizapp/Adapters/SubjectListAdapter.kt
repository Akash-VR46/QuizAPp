package com.example.quizapp.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.DataClass.Subjects
import com.example.quizapp.Fragments.SubjectChoiceFragment
import com.example.quizapp.SubjectClickListener

class SubjectListAdapter(

    private val ctxt : SubjectChoiceFragment,
    private val list : List<Subjects>,
    private val itemListener: SubjectClickListener,
    private var prevItemView: View? = null
) : RecyclerView.Adapter<SubjectListAdapter.SubjectViewHolder>()  {

    // This function creates a new ViewHolder instance and returns it.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return SubjectViewHolder(itemView)

    }

    // This function binds the data from the dataList to the views in the ViewHolder.
    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val currItem = list[position]

        holder.subjectTextView.text =currItem.subject

        holder.itemView.setOnClickListener{
            if(prevItemView == null){
                prevItemView = holder.subjectTextView
            }
            else{
                prevItemView!!.setBackgroundColor(Color.parseColor("#f2e9e4"))
                prevItemView = holder.subjectTextView
            }
            holder.subjectTextView.setBackgroundColor(Color.parseColor("#FFC1C6C6"))
            itemListener.recycleViewSubjectClicked(it,currItem.subject)



        }
    }

    //return the no. of item in the list
    override fun getItemCount(): Int {
        return list.count()
    }

    // Defining a ViewHolder class that holds a reference to the views in the list_item.xml layout file.
    class SubjectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {

        val subjectTextView: TextView =itemView.findViewById(R.id.subject_name)


    }
}

