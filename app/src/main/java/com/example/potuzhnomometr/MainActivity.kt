package com.example.potuzhnomometr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val ptzhn: Button = findViewById(R.id.button_ptzh)
        val zrd: Button = findViewById(R.id.button_zrd)
        val zrd_ptzhn: Button = findViewById(R.id.button_zrd_ptzhn)
        val test: Button = findViewById(R.id.button_test)

        ptzhn.setOnClickListener {
            val intent1 = Intent(this, Potuzhnomometr)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}