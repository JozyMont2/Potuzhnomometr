package com.example.potuzhnomometr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val ptzhn: Button = findViewById(R.id.button_ptzhn)
        val zrd: Button = findViewById(R.id.button_zrd)
        val zrdOrPtzhn: Button = findViewById(R.id.button_zrd_or_ptzhn)
        val testNaPtzhn: Button = findViewById(R.id.button_test)

        val krinzh: ImageView = findViewById(R.id.imageView_krinzh)
        val chill: ImageView = findViewById(R.id.imageView_chill)
        val nastriy: ImageView = findViewById(R.id.imageView_nastriy)

        val details: Button = findViewById(R.id.button_datails)
        val aboutApp: Button = findViewById(R.id.button_about_app)
        val aboutDeveloper: Button = findViewById(R.id.button_about_developer)

        ptzhn.setOnClickListener {
            val intent_one = Intent(this, Potuzhnomometr::class.java)
            startActivity(intent_one)
            finish()
        }

        zrd.setOnClickListener {
            val intent_two = Intent(this, Zradomometr::class.java)
            startActivity(intent_two)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}