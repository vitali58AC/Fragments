package com.example.viewandlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment)
                .commit()
        }

    }

    override fun onBackPressed() {
        val mainFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (mainFragment != null) {
            val count = mainFragment.childFragmentManager.backStackEntryCount
            if (count > 1) {
                mainFragment.childFragmentManager.popBackStack()
                return
            }
        }
        super.onBackPressed()
    }

    override fun onNewIntent(intent: Intent?) {
        Log.e("FirstAppIntent", "onNewIntent activity")
        super.onNewIntent(intent)
        setIntent(intent)
    }

}