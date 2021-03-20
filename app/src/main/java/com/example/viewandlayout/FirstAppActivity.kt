package com.example.viewandlayout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.viewandlayout.databinding.ActivityFirstAppBinding

class FirstAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstAppBinding
    private var toastCount = 0
    private var needHelpActivator = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState != null) {
            toastCount = savedInstanceState.getInt(TOAST_KEY)
            needHelpActivator = savedInstanceState.getBoolean(NEED_HELP_KEY)
        }
        if (needHelpActivator) displayHelp()
        if (toastCount == 0) {
            toast(text = "You aren't developer. Before read the guide!", length = Toast.LENGTH_LONG)
            toastCount++
        }


        binding.firstAppNeedHelp.setOnClickListener {
            needHelpClicker()
        }

        binding.firstAppCallButton.setOnClickListener {
            val phoneNumberEditTText = binding.firstAppPhoneNumber.text.toString()
            if (isValidPhone(phoneNumberEditTText)) {
                dialPhoneNumber(phoneNumberEditTText)
            } else {
                toast("Incorrect phone number")
            }
        }
        binding.creatProjectLink.setOnClickListener {
            toast("Soon")
        }
    }

    private fun toast(text: String, context: Context = this, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            context,
            text,
            length
        ).show()
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        // Вот здесь, как я прочитал в гугле, требуется либо разрешение в манифесте, либо через try/catch
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        Log.e("FirstAppIntent","onNewIntent activity")
        super.onNewIntent(intent)
        setIntent(intent)
    }
    //Оставлю это просто для истории
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                toast("Result OK from DiAl")
            } else toast("Not OK result")
        }

    private fun needHelpClicker() {
        if (!needHelpActivator) {
            displayHelp()
            needHelpActivator = true
        } else {
            binding.firstAppCallToFriend.visibility = View.GONE
            binding.firstAppPhoneNumber.visibility = View.GONE
            binding.firstAppCallButton.visibility = View.GONE
            binding.firstAppNeedHelp.text = getString(R.string.need_help)
            needHelpActivator = false
        }
    }

    private fun displayHelp() {
        binding.firstAppCallToFriend.visibility = View.VISIBLE
        binding.firstAppPhoneNumber.visibility = View.VISIBLE
        binding.firstAppCallButton.visibility = View.VISIBLE
        binding.firstAppNeedHelp.text = getString(R.string.do_not_need_help)
    }

    private fun isValidPhone(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TOAST_KEY, toastCount)
        outState.putBoolean(NEED_HELP_KEY, needHelpActivator)
    }

    companion object {
        const val TOAST_KEY = "Toast key"
        const val NEED_HELP_KEY = "Need help key"
        const val DIAL_REQUEST_CODE = 123
    }
}


