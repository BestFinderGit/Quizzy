package com.example.quiz.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.R
import com.example.quiz.activities.DATE
import com.example.quiz.activities.QuestionActivity
import com.example.quiz.models.Quiz
import com.example.quiz.utils.ColorPicker
import com.example.quiz.utils.IconPicker

class QuizAdapter(private val context: Context, private val quizzes: List<Quiz>) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.quizTitle.text = quizzes[position].title
        holder.quizCardView.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.quizIcon.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra(DATE, quizzes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var quizIcon: ImageView = itemView.findViewById(R.id.quiz_icon)
        var quizTitle: TextView = itemView.findViewById(R.id.quiz_title)
        var quizCardView: CardView = itemView.findViewById(R.id.cardView)
    }


}