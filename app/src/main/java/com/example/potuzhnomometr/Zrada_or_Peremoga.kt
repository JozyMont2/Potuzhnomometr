package com.example.potuzhnomometr

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.loadingindicator.LoadingIndicator
import kotlin.random.Random

class Zrada_or_Peremoga : AppCompatActivity() {
    enum class Mode {
        RANDOM, MANUAL, SEQUENCE
    }
    private var currentMode = Mode.RANDOM
    private lateinit var redButton2: Button
    private lateinit var greenButton: Button
    private lateinit var imageViewArrow: ImageView
    private lateinit var textMode2: TextView
    private lateinit var viewIndicate: View
    private lateinit var viewZrada_or_Peremoga: View
    private lateinit var modeButton2: Button
    private lateinit var preferanceImageView: ImageView
    private lateinit var backImageView2: ImageView
    private lateinit var view_bg: View
    private lateinit var view_bg2: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zrada_or_peremoga)

        redButton2 = findViewById(R.id.button_red2)
        greenButton = findViewById(R.id.button_green)
        imageViewArrow =findViewById(R.id.imageView_arrow)
        textMode2 = findViewById(R.id.textView_mode2)
        viewIndicate = findViewById(R.id.view_indicate)
        viewZrada_or_Peremoga = findViewById(R.id.view_zrada_or_peremoga)
        modeButton2 = findViewById(R.id.button_mode2)
        preferanceImageView = findViewById(R.id.imageView_preference2)
        backImageView2 = findViewById(R.id.imageView_back2)
        view_bg = findViewById(R.id.view41)
        view_bg2 = findViewById(R.id.view48)

        redButton2.setOnClickListener {

        }

        greenButton.setOnClickListener {

        }

        fun updateUI(){
            null
        }

        modeButton2.setOnClickListener {

        }

        preferanceImageView.setOnClickListener {

        }

        backImageView2.setOnClickListener {

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}