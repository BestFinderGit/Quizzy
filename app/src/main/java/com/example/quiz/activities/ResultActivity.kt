package com.example.quiz.activities

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.R
import com.example.quiz.models.Questions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    lateinit var question: HashMap<String, Questions>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setupViews()
    }

    private fun setupViews() {
        val result = intent.getStringExtra(QUIZ)
        val type = object : TypeToken<HashMap<String, Questions>>() {}.type
        question = Gson().fromJson(result, type)
        setUpScore()
        setupAnswer()

    }

    private fun setupAnswer() {

        val builder = StringBuilder("")
        for (entry in question.entries) {
            val question = entry.value
            builder.append("<font color='#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        }

    }

    private fun setUpScore() {
        var score = 0
        for (entry in question.entries) {
            val question = entry.value
            if (question.userAnswer == question.answer) {
                score += 10
            }
        }

        txtScore.text = "Your Score: $score"
    }
}