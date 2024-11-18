package com.example.hiskaart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hiskaart.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Используем ViewBinding для удобной работы с компонентами UI
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Настройка BottomNavigationView
        val bottomNavigationView = binding.bottomNavigation

        // Устанавливаем экран по умолчанию (Valideeri)
        openFragment(ValideeriFragment())

        // Обработка нажатий на элементы меню
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_valideeri -> {
                    openFragment(ValideeriFragment())
                    true
                }
                R.id.menu_konto -> {
                    openFragment(KontoFragment())
                    true
                }
                R.id.menu_info -> {
                    openFragment(InfoFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Метод для замены текущего фрагмента
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}