package com.example.quiz.models

data class Quiz(
    var id: String = "",
    var title: String = "",
    var questions: HashMap<String, Questions> = hashMapOf()
)
