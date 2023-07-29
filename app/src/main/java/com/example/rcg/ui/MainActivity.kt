package com.example.rcg.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rcg.R
import com.example.rcg.databinding.ActivityMainBinding
import com.example.rcg.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, MainFragment())
                .commitAllowingStateLoss()
        }
    }
}