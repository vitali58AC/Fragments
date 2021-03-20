package com.example.viewandlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView

class DeveloperDeepActivity : AppCompatActivity() {
    private var countDeep = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_deep)
        handleIntentData()
        val firstAppSource = findViewById<TextView>(R.id.firstAppSource)
        firstAppSource.setOnClickListener {
           openFirstAppActivity()
        }
        if (savedInstanceState != null) {
            countDeep = savedInstanceState.getInt("Key")
        }
        val launchModeTest = findViewById<TextView>(R.id.testLaunchMode)
        launchModeTest.setOnClickListener {
            launchModeTest.text = "Launch that activity again ($countDeep)"
            launchModeTestClick()
            countDeep++
        }
    }

    private fun handleIntentData() {
        intent.data?.let {
            val textView = findViewById<TextView>(R.id.importDeep)
            textView.text = it.toString()
        }
    }
    private fun openFirstAppActivity() {
        val activityClass = FirstAppActivity::class.java
        val FirstAppActivityIntent = Intent(
            this,
            activityClass
        )
        startActivity(FirstAppActivityIntent)
    }

    override fun onNewIntent(intent: Intent?) {
        Log.e("DeepIntent","onNewIntent activity deep")
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntentData()
    }
    private fun launchModeTestClick() {
        val activityClass = DeveloperDeepActivity::class.java
        val DeveloperDeepActivityIntent = Intent(
            this,
            activityClass
        )
        startActivity(DeveloperDeepActivityIntent)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("Key",countDeep)
    }
}
