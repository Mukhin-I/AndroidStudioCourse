package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var editTextInput: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var btnOpenSecondActivity: Button
    private lateinit var btnCallFriend: Button
    private lateinit var btnShareText: Button
    private lateinit var textViewError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        editTextInput = findViewById(R.id.editTextInput)
        editTextPhone = findViewById(R.id.editTextPhone)
        btnOpenSecondActivity = findViewById(R.id.btnOpenSecondActivity)
        btnCallFriend = findViewById(R.id.btnCallFriend)
        btnShareText = findViewById(R.id.btnShareText)
        textViewError = findViewById(R.id.textViewError)
    }

    private fun setupClickListeners() {
        btnOpenSecondActivity.setOnClickListener {
            val text = editTextInput.text.toString().trim()

            if (validateInput(text)) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("EXTRA_TEXT", text)
                startActivity(intent)
                hideError()
            } else {
                showError("Введите текст для передачи")
            }
        }

        btnCallFriend.setOnClickListener {
            val phoneNumber = editTextPhone.text.toString().trim()

            if (validatePhoneNumber(phoneNumber)) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                    hideError()
                } else {
                    showError("Приложение для звонков не найдено")
                }
            } else {
                showError("Введите корректный номер телефона")
            }
        }

        btnShareText.setOnClickListener {
            val text = editTextInput.text.toString().trim()

            if (validateInput(text)) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }

                val shareIntent = Intent.createChooser(intent, "Поделиться через")

                if (shareIntent.resolveActivity(packageManager) != null) {
                    startActivity(shareIntent)
                    hideError()
                } else {
                    showError("Нет приложений для обмена текстом")
                }
            } else {
                showError("Введите текст для обмена")
            }
        }
    }

    private fun validateInput(text: String): Boolean {
        return !TextUtils.isEmpty(text) && text.length >= 1
    }

    private fun validatePhoneNumber(phone: String): Boolean {
        if (TextUtils.isEmpty(phone)) return false
        val digitsOnly = phone.replace("\\D".toRegex(), "")
        return digitsOnly.length >= 7
    }

    private fun showError(message: String) {
        textViewError.text = message
        textViewError.visibility = TextView.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideError() {
        textViewError.visibility = TextView.GONE
    }
}