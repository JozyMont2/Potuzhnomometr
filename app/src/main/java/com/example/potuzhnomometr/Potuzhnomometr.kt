package com.example.potuzhnomometr

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Potuzhnomometr : AppCompatActivity() {

    private var currentValue = 0
    private var isPressed = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potuzhnomometr)

        val redButton: Button = findViewById(R.id.button_red)
        val valueText: TextView = findViewById(R.id.textView_value)
        val emojiText: TextView = findViewById(R.id.textView_emoji)
        val back: ImageView = findViewById(R.id.imageView_back)
        val measureView: View = findViewById(R.id.view_measure)

        val dial = mutableListOf<View>()
        for (i in 1..20) {
            val resId = resources.getIdentifier("view_$i", "id", packageName)
            val view = findViewById<View>(resId)
            dial.add(view)
        }

        back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun updateUI() {
            valueText.text = currentValue.toString()

            val activeBars = currentValue / 5
            for (i in dial.indices) {
                if (i < activeBars) {
                    dial[i].setBackgroundColor(Color.RED)
                } else {
                    dial[i].setBackgroundColor(Color.GRAY)
                }
            }

            if (isPressed) {
                measureView.setBackgroundResource(R.drawable.green)
            } else {
                measureView.setBackgroundResource(R.drawable.rounded4)
            }

            emojiText.text = when (currentValue) {
                in 0..19 -> "😪"
                in 20..39 -> "🥱"
                in 40..59 -> "😐"
                in 60..79 -> "😮"
                in 80..99 -> "🤯"
                100 -> "💀"
                else -> ""
            }

            measureView.setBackgroundColor(
                Color.argb(255, currentValue * 2, 50, 150)
            )
        }

        val runnable = object : Runnable {
            override fun run() {
                if (isPressed && currentValue < 100) currentValue++
                else if (!isPressed && currentValue > 0) currentValue--

                updateUI()
                handler.postDelayed(this, 50)
            }
        }

        redButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isPressed = true
                    handler.post(runnable)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isPressed = false
                }
            }
            true
        }
    }
}
