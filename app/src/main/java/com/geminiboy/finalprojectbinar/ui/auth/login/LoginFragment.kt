package com.geminiboy.finalprojectbinar.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentLoginBinding
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.google.android.material.textfield.TextInputLayout

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
            "Maaf, kata sandi salah",
            "Nomor telepon tidak valid",
            "Inputan yang anda masukkan tidak valid/akun belum terdaftar"
        )

        val validators = listOf(
            loginVM.isValidEmailPhone,
            loginVM.isValidPassword
        )

        var isFormValid = true
        var firstInvalidIndex: Int? = null

        for ((index, validator) in validators.withIndex()) {
            val validationMessage = validationMessages[index]

            val observer = Observer<Boolean> {
                if (!it) {
                    isFormValid = false
                    if (firstInvalidIndex == null) firstInvalidIndex = index
                    if (index == firstInvalidIndex) {
                        Toast(requireContext()).showCustomToast(
                            validationMessage,
                            requireActivity(),
                            R.layout.toast_alert_red
                        )
                        observeValidateInput()
                    }
                }
            }

            validator.observe(viewLifecycleOwner, observer)
        }

        binding.apply {
            loginVM.validateEmailPhone(etEmail.text.toString())
            loginVM.validatePassword(masukanPassword.text.toString())
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