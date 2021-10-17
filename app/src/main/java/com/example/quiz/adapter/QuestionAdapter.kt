package com.example.quiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.R
import com.example.quiz.models.Questions

class QuestionAdapter(private val context: Context, private val question: Questions) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private var options: List<String> =
        listOf(question.option1, question.option2, question.option3, question.option4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.option.text = options[position]
        holder.itemView.setOnClickListener{
            question.userAnswer=options[position]
            notifyDataSetChanged()
        }

        if(question.userAnswer==options[position]){
            holder.option.setBackgroundResource(R.drawable.option_selected_bg)
        }
        else{
            holder.option.setBackgroundResource(R.drawable.option_simple_bg)
        }

    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var option: TextView = itemView.findViewById(R.id.quizOption)
    }
}