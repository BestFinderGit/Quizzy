package com.example.quiz.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quiz.R
import com.example.quiz.adapter.QuizAdapter
import com.example.quiz.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore
    private val adapter = QuizAdapter(this, quizList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()


    }

    private fun setupViews() {
        setupDrawer()
        setupRecycleView()
        setupFireStore()
        setupDatePicker()
    }

    private fun setupDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date: String = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra(DATE, date)
                startActivity(intent)
            }
            datePicker.addOnCancelListener {
                Toast.makeText(this, "DatePicker was cancelled", Toast.LENGTH_SHORT).show()
            }

            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection(QUIZZES)
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()

        }

    }

    private fun setupRecycleView() {
        quiz_recycleView.layoutManager = GridLayoutManager(this, 2)
        quiz_recycleView.adapter = adapter
    }

    private fun setupDrawer() {
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.menu_profile) {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawers()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}