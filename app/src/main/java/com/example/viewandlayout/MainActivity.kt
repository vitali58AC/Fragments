package com.example.viewandlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.viewandlayout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: com.example.viewandlayout.databinding.ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment == null) {
            val fragment = LoginFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        Log.e("FirstAppIntent","onNewIntent activity")
        super.onNewIntent(intent)
        setIntent(intent)
    }

  }