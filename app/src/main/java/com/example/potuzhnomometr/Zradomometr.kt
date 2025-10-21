package com.example.potuzhnomometr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class Zradomometr : AppCompatActivity() {
    enum class Mode {
        HOLD, RANDOM, MANUAL
    }
    private var currentMode = Mode.HOLD
    private var growthDelay = 85L
    private var fallDelay = 85L
    private var currentValue = 0
    private var isPressed = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var redButton: Button
    private lateinit var modeButton: Button
    private lateinit var modeText: TextView
    private lateinit var valueText: TextView
    private lateinit var emojiText: TextView
    private lateinit var measureView: View
    private lateinit var seekBar: SeekBar
    private lateinit var dial: List<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potuzhnomometr)

        redButton = findViewById(R.id.button_red)
        modeButton = findViewById(R.id.button_mode)
        modeText = findViewById(R.id.textView_mode)
        valueText = findViewById(R.id.textView_value)
        emojiText = findViewById(R.id.textView_emoji)
        measureView = findViewById(R.id.view_measure)
        seekBar = findViewById(R.id.seekBar_manual)

        val back: ImageView = findViewById(R.id.imageView_back)
        val preference: ImageView = findViewById(R.id.imageView_preference)

        dial = listOf(
            findViewById(R.id.view_1), findViewById(R.id.view_2), findViewById(R.id.view_3),
            findViewById(R.id.view_4), findViewById(R.id.view_5), findViewById(R.id.view_6),
            findViewById(R.id.view_7), findViewById(R.id.view_8), findViewById(R.id.view_9),
            findViewById(R.id.view_10), findViewById(R.id.view_11), findViewById(R.id.view_12),
            findViewById(R.id.view_13), findViewById(R.id.view_14), findViewById(R.id.view_15),
            findViewById(R.id.view_16), findViewById(R.id.view_17), findViewById(R.id.view_18),
            findViewById(R.id.view_19), findViewById(R.id.view_20)
        )

        seekBar.visibility = View.GONE

        val updateUI = {
            measureView.setBackgroundResource(
                if (currentValue > 0) {
                    R.drawable.green
                } else {
                    R.drawable.rounded4
                }
            )

            valueText.text = currentValue.toString()
            emojiText.text = when (currentValue) {
                in 0..20 -> "😪"
                in 21..40 -> "🥱"
                in 41..60 -> "😐"
                in 61..80 -> "😮"
                in 81..99 -> "🤯"
                else -> "💀"
            }

            val activeBars = currentValue / 5
            for (i in dial.indices) {
                dial[i].setBackgroundResource(
                    if (i < activeBars) R.drawable.green_view else R.drawable.white_view
                )
            }
        }
        updateUI()

        val increaseRunnable = object : Runnable {
            override fun run() {
                if (isPressed && currentValue < 100) {
                    currentValue++
                    updateUI()
                    handler.postDelayed(this, growthDelay)
                }
            }
        }

        val decreaseRunnable = object : Runnable {
            override fun run() {
                if (!isPressed && currentValue > 0) {
                    currentValue--
                    updateUI()
                    handler.postDelayed(this, fallDelay)
                } else handler.removeCallbacks(this)
            }
        }

        redButton.setOnTouchListener { _, event ->
            when (currentMode) {

                Mode.HOLD -> {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isPressed = true
                            handler.removeCallbacks(decreaseRunnable)
                            handler.post(increaseRunnable)
                            updateUI()
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            isPressed = false
                            handler.removeCallbacks(increaseRunnable)
                            handler.post(decreaseRunnable)
                            updateUI()
                        }
                    }
                    true
                }

                Mode.RANDOM -> {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        val randomValue = Random.nextInt(0, 100)
                        currentValue = randomValue
                        updateUI()
                        showRandomMessage(randomValue)
                    }
                    true
                }

                Mode.MANUAL -> true
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (currentMode == Mode.MANUAL) {
                    currentValue = progress
                    updateUI()
                }
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        modeButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.mods, null)
            val dialog = AlertDialog.Builder(this).setView(dialogView).create()

            dialogView.findViewById<ImageView>(R.id.imageView_closewindow2).setOnClickListener {
                dialog.dismiss()
            }

            dialogView.findViewById<Button>(R.id.buttonmode_ytumannya).setOnClickListener {
                setMode(Mode.HOLD, "Утримання", dialog)
            }

            dialogView.findViewById<Button>(R.id.buttonmode_random).setOnClickListener {
                setMode(Mode.RANDOM, "Рандом", dialog)
            }

            dialogView.findViewById<Button>(R.id.buttonmode_rychnuy).setOnClickListener {
                setMode(Mode.MANUAL, "Ручний", dialog)
            }

            dialog.show()
        }

        setupPreference(preference)

        back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun showRandomMessage(value: Int) {
        val message = when (value) {
            in 0..20 -> "Низький рівень концентрації"
            in 21..40 -> "Середній рівень концентрації"
            in 41..60 -> "Вище середнього"
            in 61..80 -> "Високий рівень концентрації"
            else -> "Критичний рівень!"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setMode(mode: Mode, label: String, dialog: AlertDialog) {
        currentMode = mode
        dialog.dismiss()
        modeText.text = label
        Toast.makeText(this, "Поточний режим: $label", Toast.LENGTH_SHORT).show()

        when (mode) {
            Mode.HOLD, Mode.RANDOM -> {
                redButton.visibility = View.VISIBLE
                seekBar.visibility = View.GONE
                emojiText.text = "😪"
                for (i in dial.indices){
                    dial[i].setBackgroundResource(R.drawable.white_view)
                }
                currentValue = 0
                valueText.text = "0"
            }
            Mode.MANUAL -> {
                redButton.visibility = View.GONE
                seekBar.visibility = View.VISIBLE
                seekBar.progress = currentValue
                emojiText.text = "😪"
                for (i in dial.indices){
                    dial[i].setBackgroundResource(R.drawable.white_view)
                }
                currentValue = 0
                valueText.text = "0"
            }
        }
        isPressed = false
    }

    private fun setupPreference(preference: ImageView) {
        val dialogView2 = layoutInflater.inflate(R.layout.preference, null)
        val dialog2 = AlertDialog.Builder(this).setView(dialogView2).create()

        dialogView2.findViewById<ImageView>(R.id.imageView_closewindow3).setOnClickListener {
            dialog2.dismiss()
        }

        val growViews = listOf(
            dialogView2.findViewById<View>(R.id.view1_1),
            dialogView2.findViewById<View>(R.id.view2_2),
            dialogView2.findViewById<View>(R.id.view3_3),
            dialogView2.findViewById<View>(R.id.view4_4)
        )

        val fallViews = listOf(
            dialogView2.findViewById<View>(R.id.view11_11),
            dialogView2.findViewById<View>(R.id.view22_22),
            dialogView2.findViewById<View>(R.id.view33_33),
            dialogView2.findViewById<View>(R.id.view44_44)
        )

        val delayValues = listOf(100L, 70L, 40L, 20L)

        fun setBackground(views: List<View>, selectedIndex: Int) {
            views.forEachIndexed { index, view ->
                view.setBackgroundResource(
                    if (index == selectedIndex) R.drawable.background_for_preferanceview else R.drawable.rounded2
                )
            }
        }

        growViews.forEachIndexed { index, v ->
            v.setOnClickListener {
                growthDelay = delayValues[index]
                setBackground(growViews, index)
            }
        }

        fallViews.forEachIndexed { index, v ->
            v.setOnClickListener {
                fallDelay = delayValues[index]
                setBackground(fallViews, index)
            }
        }

        preference.setOnClickListener {
            val initialGrowIndex = delayValues.indexOf(growthDelay)
            if (initialGrowIndex != -1) {
                setBackground(growViews, initialGrowIndex)
            } else {
                setBackground(growViews, -1)
            }
            val initialFallIndex = delayValues.indexOf(fallDelay)
            if (initialFallIndex != -1) {
                setBackground(fallViews, initialFallIndex)
            } else {
                setBackground(fallViews, -1)
            }

            dialog2.show()
        }
    }
}