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

        val ptzhn: Button = findViewById(R.id.button_ptzh)
        val zrd: Button = findViewById(R.id.button_zrd)
        val zrd_ptzhn: Button = findViewById(R.id.button_zrd_ptzhn)
        val test: Button = findViewById(R.id.button_test)

        val chilomometr: ImageView = findViewById(R.id.imageView_chilomometr)
        val krinzhomometr: ImageView = findViewById(R.id.imageView_krinzhomometr)
        val radmomaizer_of_mood: ImageView = findViewById(R.id.imageView_radmomaizer_of_mood)

        val more_details: Button = findViewById(R.id.button_more_details)
        val about_app: Button = findViewById(R.id.button_about_app)
        val about_developer: Button = findViewById(R.id.button_about_developer)

        ptzhn.setOnClickListener {
            val intentOne = Intent(this, Potuzhnomometr::class.java)
            startActivity(intentOne)
            finish()
        }

        zrd.setOnClickListener {
            val intentTwo = Intent(this, Zradomometr::class.java)
            startActivity(intentTwo)
            finish()
        }

        zrd_ptzhn.setOnClickListener {
            val intentThree = Intent(this, Zrada_or_Peremoga::class.java)
            startActivity(intentThree)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}