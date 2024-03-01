package com.example.earningapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val register = findViewById<TextView>(R.id.registerbutton)
        register.setOnClickListener {
            intent = Intent(this, Sign_Up::class.java)
            startActivity(intent)
        }
    }
}