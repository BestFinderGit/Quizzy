package com.example.quiz.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.R
import com.example.quiz.adapter.QuestionAdapter
import com.example.quiz.models.Questions
import com.example.quiz.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*


class QuestionActivity : AppCompatActivity() {
    var questions: HashMap<String, Questions>? = null
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        setupFireStore()
        setupButtonClick()
    }

    private fun setupButtonClick() {
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        btnNext.setOnClickListener {
            index++
            bindViews()
        }

        btnSubmit.setOnClickListener {

            val intent = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(questions)
            intent.putExtra(QUIZ, json)
            startActivity(intent)
            finish()
        }
    }

    private fun setupFireStore() {
        val fireStore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra(DATE)

        if (date != null && date != "") {
            fireStore.collection(QUIZZES).document(date)
                .get()
                .addOnSuccessListener {
                    if (it != null && it.exists()) {
                        questions = it.toObject(Quiz::class.java)!!.questions
                        bindViews()
                    } else {
                        Toast.makeText(this, "No Quiz For Selected Date", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        }

    }

    private fun bindViews() {

        btnPrevious.visibility = View.GONE
        btnNext.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        if (index == 1 && index != questions!!.size) {
            btnNext.visibility = View.VISIBLE
        } else if (index == questions!!.size) {
            if (index == 1) {
                btnSubmit.visibility = View.VISIBLE
            } else {
                btnSubmit.visibility = View.VISIBLE
                btnPrevious.visibility = View.VISIBLE
            }

        } else {
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["$QUESTION$index"]
        question?.let {
            txtDescription.text = it.description
            val adapter = QuestionAdapter(this, it)
            questionRecycleView.layoutManager = LinearLayoutManager(this)
            questionRecycleView.adapter = adapter
            questionRecycleView.setHasFixedSize(true)
        }
    }

}