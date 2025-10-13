package com.example.potuzhnomometr

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Potuzhnomometr : AppCompatActivity() {
    private var currentValue = 0
    private var isPressed = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potuzhnomometr)

        val redButton: Button = findViewById(R.id.button_red)
        val modeButton: Button = findViewById(R.id.button_mode)
        val modeText: TextView = findViewById(R.id.textView_mode)
        val valueText: TextView = findViewById(R.id.textView_value)
        val emojiText: TextView = findViewById(R.id.textView_emoji)
        val back: ImageView = findViewById(R.id.imageView_back)
        val preference: ImageView = findViewById(R.id.imageView_preference)
        val measureView: View = findViewById(R.id.view_measure)

        val dial = listOf(
            findViewById<View>(R.id.view_1), findViewById(R.id.view_2), findViewById(R.id.view_3),
            findViewById(R.id.view_4), findViewById(R.id.view_5), findViewById(R.id.view_6),
            findViewById(R.id.view_7), findViewById(R.id.view_8), findViewById(R.id.view_9),
            findViewById(R.id.view_10), findViewById(R.id.view_11), findViewById(R.id.view_12),
            findViewById(R.id.view_13), findViewById(R.id.view_14), findViewById(R.id.view_15),
            findViewById(R.id.view_16), findViewById(R.id.view_17), findViewById(R.id.view_18),
            findViewById(R.id.view_19), findViewById(R.id.view_20)
        )

        val updateUI = {
            valueText.text = currentValue.toString()

            emojiText.text = when (currentValue) {
                in 0..20 -> "😪"
                in 21..40 -> "🥱"
                in 41..60 -> "😐"
                in 61..80 -> "😮"
                in 81..99 -> "🤯"
                100 -> "💀"
                else -> "💀"
            }

            val activeBars = currentValue / 5
            for (i in dial.indices) {
                if (i < activeBars) {
                    dial[i].setBackgroundResource(R.drawable.green_view)
                } else {
                    dial[i].setBackgroundResource(R.drawable.white_view)
                }
            }

            if (isPressed) {
                measureView.setBackgroundResource(R.drawable.green)
            } else {
                measureView.setBackgroundResource(R.drawable.rounded4)
            }
        }
        updateUI()

        val increaseRunnable = object : Runnable {
            override fun run() {
                if (isPressed && currentValue < 100) {
                    currentValue++
                    updateUI()
                    handler.postDelayed(this, 50)
                }
            }
        }
        val decreaseRunnable = object : Runnable {
            override fun run() {
                if (!isPressed && currentValue > 0) {
                    currentValue--
                    updateUI()
                    handler.postDelayed(this, 50)
                } else {
                    handler.removeCallbacks(this)
                }
            }
        }

        redButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isPressed = true
                    handler.removeCallbacks(decreaseRunnable)
                    handler.post(increaseRunnable)
                    updateUI()
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isPressed = false
                    handler.removeCallbacks(increaseRunnable)
                    handler.post(decreaseRunnable)
                    updateUI()
                    true
                }
                else -> false
            }
        }

        modeButton.setOnClickListener {
            val dialogeView = layoutInflater.inflate(R.layout.mods, null)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogeView)
                .create()
            dialogeView.findViewById<ImageView>(R.id.imageView_closewindow).setOnClickListener {
                dialog.dismiss()
            }
            dialogeView.findViewById<Button>(R.id.buttonmode_ytumannya).setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this, "Поточний режим: Утримання", Toast.LENGTH_SHORT).show()
                modeText.text = "Утримання"
            }
            dialogeView.findViewById<Button>(R.id.buttonmode_random).setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this, "Поточний режим: Рандом", Toast.LENGTH_SHORT).show()
                modeText.text = "Рандом"
            }
            dialogeView.findViewById<Button>(R.id.buttonmode_rychnuy).setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this, "Поточний режим: Ручний", Toast.LENGTH_SHORT).show()
                modeText.text = "Ручний"
            }

            dialog.show()
        }

        preference.setOnClickListener {
            val dialogView2 = layoutInflater.inflate(R.layout.preference, null)
            val dialog2 = AlertDialog.Builder(this)
                .setView(dialogView2)
                .create()
            dialogView2.findViewById<ImageView>(R.id.imageView_closewindow3).setOnClickListener {
                dialog2.dismiss()
            }
            dialog2.show()
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}