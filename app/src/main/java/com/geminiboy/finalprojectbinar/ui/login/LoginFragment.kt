package com.geminiboy.finalprojectbinar.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentLoginBinding
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                observeValidateAll()
            }
        }
    }

    private fun observeValidateAll() {
        val validationMessages = listOf(
            "Alamat email tidak terdaftar!",
            "Maaf, kata sandi salah"
        )

        val validators = listOf(
            loginVM.isValidEmailPhone,
            loginVM.isValidPassword
        )

        observeValidateInput()
        var isFormValid = true
        val validationMessageList = validators.mapIndexedTo(mutableListOf()) { index, _ ->
            MutableLiveData<String>().apply {
                value = validationMessages[index]
            }
        }

        for ((index, validator) in validators.withIndex()) {
            val validationMessage = validationMessageList[index]

            val observer = Observer<Boolean> { isValid ->
                if (!isValid) {
                    isFormValid = false
                }
                validationMessage.value = if (isValid) null else validationMessages[index]
            }

            validator.observe(viewLifecycleOwner, observer)
        }

        val fields = listOf(
            binding.etEmail,
            binding.masukanPassword
        )

        val isFieldsNotEmpty = fields.all { it.text.toString().isNotEmpty() }
        if (isFormValid && isFieldsNotEmpty) {
            Toast(requireContext()).showCustomToast(
                "Form valid",
                requireActivity(),
                R.layout.toast_alert_green
            )
        }else {
            val invalidMessage = validationMessageList.find { it.value != null }?.value
            if (invalidMessage != null) {
                Toast(requireContext()).showCustomToast(
                    invalidMessage,
                    requireActivity(),
                    R.layout.toast_alert_red
                )
            }
        }
    }

    private fun observeValidateInput() {
        binding.apply {
            val validations = listOf(
                loginVM.isValidEmailPhone to Email,
                loginVM.isValidPassword to Password
            )

            for ((validator, textInputLayout) in validations) {
                validator.observe(viewLifecycleOwner) {
                    if (!it) {
                        textInputLayout.error = " "
                    } else {
                        textInputLayout.error = null
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}