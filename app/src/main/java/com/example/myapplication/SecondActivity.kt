package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {

    private lateinit var textViewReceived: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initViews()
        receiveData()
        setupClickListeners()
    }

    private fun initViews() {
        textViewReceived = findViewById(R.id.textViewReceived)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun receiveData() {
        val receivedText = intent.getStringExtra("EXTRA_TEXT") ?: "Нет данных"
        textViewReceived.text = "Полученный текст:\n\n$receivedText"
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}