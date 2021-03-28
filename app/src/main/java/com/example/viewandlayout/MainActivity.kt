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

/*    override fun onBackPressed() {
        supportFragmentManager.findFragmentByTag("1")
            ?.childFragmentManager?.takeIf {
                Log.e("MainActivity", "ЧИСЛО ${it.backStackEntryCount}")
                it.backStackEntryCount > 0
            }?.popBackStack() ?: super.onBackPressed()
    }*/

    //Здесь нужно пояснение) Скопированный java код.
    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible && hasBackStack(fragment)) {
                if (popFragment(fragment)) return
            }
        }
        super.onBackPressed()
    }

    private fun hasBackStack(fragment: Fragment): Boolean {
        val childFragmentManager: FragmentManager = fragment.childFragmentManager
        return childFragmentManager.backStackEntryCount > 0
    }

    private fun popFragment(fragment: Fragment): Boolean {
        val fragmentManager: FragmentManager = fragment.childFragmentManager
        for (childFragment in fragmentManager.fragments) {
            if (childFragment.isVisible) {
                return if (hasBackStack(childFragment)) {
                    popFragment(childFragment)
                } else {
                    fragmentManager.popBackStack()
                    true
                }
            }
        }
        return false
    }



    override fun onNewIntent(intent: Intent?) {
        Log.e("FirstAppIntent", "onNewIntent activity")
        super.onNewIntent(intent)
        setIntent(intent)
    }

}