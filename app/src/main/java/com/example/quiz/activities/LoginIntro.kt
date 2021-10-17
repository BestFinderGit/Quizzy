package com.example.quiz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_intro.*


const val QUESTION = "question"
const val QUIZZES = "QUIZZES"
const val DATE = "DATE"
const val QUIZ = "QUIZ"
const val MAIN = "MAIN"
const val LOGIN = "LOGIN"

class LoginIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)
        val firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            redirect(MAIN)
        }

        btnGetStarted.setOnClickListener {
            redirect(LOGIN)
        }
    }

    private fun redirect(screen: String) {

        val intent = when (screen) {
            LOGIN -> Intent(this, LoginActivity::class.java)
            MAIN -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no path exists")
        }

        startActivity(intent)
        finish()

    }
}