package com.example.viewandlayout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.viewandlayout.databinding.FragmentDetailFirstAppBinding

class DetailFragmentFirstApp() : Fragment() {
    private var _binding: FragmentDetailFirstAppBinding? = null
    private val binding get() = _binding!!
    private var toastCount = 0
    private var needHelpActivator = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailFirstAppBinding.inflate(inflater, container, false)
        val view = binding.root

        if (savedInstanceState != null) {
            toastCount = savedInstanceState.getInt(TOAST_KEY)
            needHelpActivator = savedInstanceState.getBoolean(NEED_HELP_KEY)
        }
        if (needHelpActivator) displayHelp()
        if (toastCount == 0) {
            /*Toast.makeText(activity?.baseContext, "You aren't developer. Before read the guide!", Toast.LENGTH_LONG)
                .show()*/
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

        return view
    }

    private fun toast(text: String, context: Context = Activity(), length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            activity?.baseContext,
            text,
            length
        ).show()
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        // Вот здесь, как я прочитал в гугле, требуется либо разрешение в манифесте, либо через try/catch
        if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
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
    }

}