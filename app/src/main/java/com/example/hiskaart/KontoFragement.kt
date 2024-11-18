package com.example.hiskaart

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hiskaart.R

class KontoFragment : Fragment() {

    private val username = "admin"
    private val password = "KILLridango"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_konto, container, false)

        // Инициализация полей и кнопок
        val usernameField = view.findViewById<EditText>(R.id.kasutajanimi_field)
        val passwordField = view.findViewById<EditText>(R.id.parool_field)
        val loginButton = view.findViewById<Button>(R.id.logi_sisse_button)
        val logoutButton = view.findViewById<Button>(R.id.logi_valja_button)
        val loginLayout = view.findViewById<View>(R.id.login_layout)
        val loggedInLayout = view.findViewById<View>(R.id.logged_in_layout)

        // Получаем состояние из SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("LoginState", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // Обновляем UI в зависимости от состояния
        if (isLoggedIn) {
            loginLayout.visibility = View.GONE
            loggedInLayout.visibility = View.VISIBLE
        } else {
            loginLayout.visibility = View.VISIBLE
            loggedInLayout.visibility = View.GONE
        }

        // Анимация кнопки Logi Sisse
        loginButton.setOnClickListener {
            animateButtonColor(loginButton, Color.BLACK, Color.parseColor("#3399FF"))

            val enteredUsername = usernameField.text.toString()
            val enteredPassword = passwordField.text.toString()

            if (enteredUsername == username && enteredPassword == password) {
                // Сохраняем состояние входа
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                // Обновляем UI
                loginLayout.visibility = View.GONE
                loggedInLayout.visibility = View.VISIBLE
            } else {
                // Если логин неудачен
                val errorText = view.findViewById<TextView>(R.id.error_message)
                errorText.text = "Vale kasutajanimi või parool"
                errorText.visibility = View.VISIBLE
            }
        }

        // Логика кнопки Logi Välja
        logoutButton.setOnClickListener {
            // Сбрасываем состояние входа
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

            // Обновляем UI
            loginLayout.visibility = View.VISIBLE
            loggedInLayout.visibility = View.GONE

            // Очищаем поля ввода
            usernameField.text.clear()
            passwordField.text.clear()
        }

        return view
    }

    private fun animateButtonColor(button: Button, startColor: Int, endColor: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor, startColor)
        colorAnimation.duration = 1000 // Длительность анимации в миллисекундах
        colorAnimation.addUpdateListener { animator ->
            button.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
    }
}