package com.example.viewandlayout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.BuildConfig
import com.bumptech.glide.Glide
import com.example.viewandlayout.databinding.FragmentLoginBinding

class LoginFragment() : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var validateState = FormState(false, "", false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        val image = binding.imageGlide
        //Загрузка изображения с интернета(ссылка)
        val urlLogo = getString(R.string.url_logoDev)
        //Настройки Glide
        Glide.with(this)
            .load(urlLogo)
            .into(image);


        //Восстановление состояния ошибки формы логина и функция вывода ошибки из parcelable
        if (savedInstanceState != null) {
            validateState = savedInstanceState.getParcelable<FormState>("Parcel")
                ?: error("Null in parcel")
        }
        binding.loginButton.isEnabled = validateState.buttonValid
        fun callState() {
            if (validateState.valid) {
                binding.errorTextView.visibility = View.GONE
            } else {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = validateState.message
            }
        }
        callState()
        //Обработка нажатия на кнопку
        binding.loginButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (checkValidationMail() && checkValidatePass()) {
                    validateState = validateState.validateTrue()
                    waitLogin {
                        parentFragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                            )
                            replace(R.id.fragmentContainer, MainFragment())
                        }
                    }
                } else {
                    validateState = validateState.validateFalse()
                    if (!checkValidatePass()) {
                        validateState = validateState.errorMessage(getString(R.string.short_pass))
                    } else if (!checkValidationMail()) {
                        validateState = validateState.errorMessage(getString(R.string.not_email))
                    } else {
                        validateState =
                            validateState.errorMessage(getString(R.string.bad_email_pass))
                    }
                    callState()
                }
            }
        })

        //Добавление новой кнопки ANR
        binding.longOperationButton.setOnClickListener {
            Thread.sleep(7000)
        }

        //Реализация чебкокса
        binding.checkDevelop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                buttonAvailable()
            }
        })

        //Эту конструкцию добавил просто для пробы примера из урока, выводит введеный email
        binding.inputMail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginText.text = s?.takeIf { it.isNotBlank() }?.let { name ->
                    resources.getString(R.string.messageAfterLogin, name)
                }
            }
        })

        //Проверка условий активации кнопки
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.loginButton.isEnabled =
                    binding.inputMail.text.toString()
                        .isNotBlank() && binding.inputPass.text.toString()
                        .isNotBlank() && binding.checkDevelop.isChecked
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        binding.inputMail.addTextChangedListener(watcher)
        binding.inputPass.addTextChangedListener(watcher)

        return view
    }


    object DebugLogger {
        fun i(tagI: String, msg: String) {
            if (BuildConfig.DEBUG) {
                Log.i(tagI, msg)
            }
        }
    }

    private fun waitLogin(callback: () -> Unit) {
        val barToAdd = ProgressBar(activity).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        binding.childContainer.addView(barToAdd)
        barToAdd.visibility = View.VISIBLE
        updateWaiteLoginState(false)
        Handler(Looper.getMainLooper()).postDelayed({
            barToAdd.visibility = View.GONE
            updateWaiteLoginState(true)
            callback()
        }, 2000)
    }

    //Решение из интернета на проверку email на валидность формы, работает отлично.
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkValidationMail(): Boolean {
        val text = binding.inputMail.text.toString()
        return isValidEmail(text)

    }

    private fun checkValidatePass(): Boolean {
        val lengthPass = binding.inputPass.text.toString().length
        return lengthPass >= 7
    }

    private fun updateWaiteLoginState(enable: Boolean) {
        binding.loginButton.isEnabled = enable
        binding.checkDevelop.isClickable = enable
        binding.inputMail.isEnabled = enable
        binding.inputPass.isEnabled = enable
    }

    private fun buttonAvailable() {
        binding.loginButton.isEnabled =
            binding.inputMail.text.toString()
                .isNotBlank() && binding.inputPass.text.toString()
                .isNotBlank() && binding.checkDevelop.isChecked
        if (binding.loginButton.isEnabled) {
            validateState.validateButton(true)
        } else validateState.validateButton(false)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("button", binding.loginButton.isEnabled)
        //сохранение парцел объекта
        outState.putParcelable("Parcel", validateState)
    }

    override fun onResume() {
        super.onResume()
        buttonAvailable()
    }

    override fun onDestroy() {
        super.onDestroy()
           _binding = null
    }

}