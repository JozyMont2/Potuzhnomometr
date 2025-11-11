package com.example.potuzhnomometr

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.animation.ObjectAnimator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import kotlin.random.Random
import android.os.Handler
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Zrada_or_Peremoga : AppCompatActivity() {

    enum class Mode {
        RANDOM, MANUAL, SEQUENCE
    }

    private var currentMode = Mode.RANDOM
    private var lastWasZrada = false

    private lateinit var redButton2: Button
    private lateinit var greenButton: Button
    private lateinit var imageViewArrow: ImageView
    private lateinit var textMode2: TextView
    private lateinit var textMode3: TextView
    private lateinit var viewZrada_or_Peremога: View
    private lateinit var modeButton2: Button
    private lateinit var preferanceImageView: ImageView
    private lateinit var backImageView2: ImageView
    private lateinit var view_bg1: View
    private lateinit var view_bg2: View
    private lateinit var view_for_bg_greenButton: View

    private val handler = Handler(Looper.getMainLooper())
    private val RESET_DELAY: Long = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zrada_or_peremoga)

        // Ініціалізація елементів
        redButton2 = findViewById(R.id.button_red2)
        greenButton = findViewById(R.id.button_green)
        imageViewArrow = findViewById(R.id.imageView_arrow)
        textMode2 = findViewById(R.id.textView_mode2)
        textMode3 = findViewById(R.id.textView_mode3)
        viewZrada_or_Peremога = findViewById(R.id.view_zrada_or_peremoga)
        modeButton2 = findViewById(R.id.button_mode2)
        preferanceImageView = findViewById(R.id.imageView_preference2)
        backImageView2 = findViewById(R.id.imageView_back2)
        view_bg1 = findViewById(R.id.viewbg)
        view_bg2 = findViewById(R.id.viewbg2)
        view_for_bg_greenButton = findViewById(R.id.viewbg_for_greenbutton)

        imageViewArrow.post {
            // Центр по горизонталі
            imageViewArrow.pivotX = (imageViewArrow.width / 2).toFloat()
            // Точка обертання ближче до низу (0.9f = 90% висоти)
            imageViewArrow.pivotY = imageViewArrow.height * 0.9f
        }

        updateUI()

        // Червона кнопка
        redButton2.setOnClickListener {
            when (currentMode) {
                Mode.RANDOM -> {
                    val isZrada = Random.nextBoolean()
                    val targetAngle = if (isZrada) -45f else 45f
                    animateArrow(targetAngle, if (isZrada) "Зрада" else "Перемога")
                }
                Mode.SEQUENCE -> {
                    val isZrada = !lastWasZrada
                    val targetAngle = if (isZrada) -45f else 45f
                    animateArrow(targetAngle, if (isZrada) "Зрада" else "Перемога")
                    lastWasZrada = isZrada
                }
                Mode.MANUAL -> {
                    animateArrow(-45f, "Зрада")
                }
            }
        }

        // Зелена кнопка
        greenButton.setOnClickListener {
            if (currentMode == Mode.MANUAL) {
                animateArrow(45f, "Перемога")
            } else {
                Toast.makeText(this, "Зелена кнопка активна лише в ручному режимі", Toast.LENGTH_SHORT).show()
            }
        }

        // Кнопка вибору режиму
        modeButton2.setOnClickListener {
            showDialog(R.layout.mods2) { view, dialog ->
                view.findViewById<ImageView>(R.id.imageView_closewindow2).setOnClickListener {
                    dialog.dismiss()
                }
                view.findViewById<Button>(R.id.buttonmode_random2).setOnClickListener {
                    setMode(Mode.RANDOM, "Рандом", dialog)
                }
                view.findViewById<Button>(R.id.buttonmode_manual2).setOnClickListener {
                    setMode(Mode.MANUAL, "Ручний", dialog)
                }
                view.findViewById<Button>(R.id.buttonmode_sequence).setOnClickListener {
                    setMode(Mode.SEQUENCE, "Послідовність", dialog)
                    textMode2.setTextSize(13f)
                }
            }
        }

        // Кнопка налаштувань
        preferanceImageView.setOnClickListener {
            showDialog(R.layout.preference2) { view, dialog ->
                view.findViewById<ImageView>(R.id.imageView_closewindow).setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        // Кнопка назад
        backImageView2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        // Відступи для системних панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Початковий вигляд
    private fun updateUI() {
        textMode2.text = "Рандом"
        currentMode = Mode.RANDOM
        greenButton.visibility = View.GONE
        view_bg1.visibility = View.VISIBLE
        view_bg2.visibility = View.VISIBLE
        view_for_bg_greenButton.visibility = View.VISIBLE
    }

    // Повернення стрілки до 0°
    private fun resetArrowRotation() {
        val animator = ObjectAnimator.ofFloat(imageViewArrow, "rotation", imageViewArrow.rotation, 0f)
        animator.duration = 500
        animator.start()
    }

    // Анімація стрілки
    private fun animateArrow(targetAngle: Float, result: String) {
        // Обертання
        val rotateAnimator = ObjectAnimator.ofFloat(imageViewArrow, "rotation", imageViewArrow.rotation, targetAngle)
        rotateAnimator.duration = 500

        // Опускання вниз
        val fallAnimator = ObjectAnimator.ofFloat(imageViewArrow, "translationY", 0f, 25f)
        fallAnimator.duration = 500

        // Повернення вгору
        val riseAnimator = ObjectAnimator.ofFloat(imageViewArrow, "translationY", 25f, 0f)
        riseAnimator.duration = 500

        // Запуск обох одночасно
        val set = AnimatorSet()
        set.playTogether(rotateAnimator, fallAnimator)
        set.start()

        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                super.onAnimationEnd(animation)

                viewZrada_or_Peremога.setBackgroundResource(
                    if (result == "Зрада") R.drawable.red2 else R.drawable.green
                )

                Toast.makeText(applicationContext, if (result == "Зрада") "ЗРАДА 💀" else "ПЕРЕМОГА ⚡", Toast.LENGTH_SHORT).show()

                handler.postDelayed({
                    val resetSet = AnimatorSet()
                    resetSet.playTogether(
                        ObjectAnimator.ofFloat(imageViewArrow, "rotation", imageViewArrow.rotation, 0f),
                        riseAnimator
                    )
                    resetSet.start()
                }, RESET_DELAY)
            }
        })
    }

    // Зміна режиму
    private fun setMode(mode: Mode, label: String, dialog: AlertDialog) {
        currentMode = mode
        dialog.dismiss()
        textMode2.text = label
        Toast.makeText(this, "Поточний режим $label", Toast.LENGTH_SHORT).show()
        viewZrada_or_Peremога.setBackgroundResource(R.drawable.rounded_view)

        when (mode) {
            Mode.RANDOM, Mode.SEQUENCE -> {
                view_for_bg_greenButton.visibility = View.GONE
                greenButton.visibility = View.GONE
                textMode2.visibility = View.VISIBLE
                textMode3.visibility = View.VISIBLE
                view_bg1.visibility = View.VISIBLE
                view_bg2.visibility = View.VISIBLE
            }
            Mode.MANUAL -> {
                view_for_bg_greenButton.visibility = View.VISIBLE
                greenButton.visibility = View.VISIBLE
                textMode2.visibility = View.GONE
                textMode3.visibility = View.GONE
                view_bg1.visibility = View.GONE
                view_bg2.visibility = View.GONE
            }
        }
    }

    // Універсальна функція створення діалогів
    private fun showDialog(layoutId: Int, onViewReady: (View, AlertDialog) -> Unit) {
        val dialogView = layoutInflater.inflate(layoutId, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).create()
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.6f)
        }
        onViewReady(dialogView, dialog)
        dialog.show()
    }
}
