package com.geminiboy.finalprojectbinar.ui.auth.register

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
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerVM: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
           masukanNama.addTextChangedListener {
               registerVM.validateName(masukanNama.text.toString())
           }

            masukanEmail.addTextChangedListener {
                registerVM.validateEmail(masukanEmail.text.toString())
            }

            masukanTelepon.addTextChangedListener {
                registerVM.phoneNumber(masukanTelepon.text.toString())
            }

            masukanPassword.addTextChangedListener {
                registerVM.validatePassword(masukanPassword.text.toString())
            }

            btnDaftar.setOnClickListener {
                registerVM.validateName(masukanNama.text.toString())
                registerVM.validateEmail(masukanEmail.text.toString())
                registerVM.phoneNumber(masukanTelepon.text.toString())
                registerVM.validatePassword(masukanPassword.text.toString())

                observeValidateAll()
            }
        }
        observeValidate()
    }

    private fun TextInputLayout.setValidationState(isValid: Boolean) {
        val colorResId = if (isValid) R.color.Green else R.color.Red
        val iconResId = if (isValid) R.drawable.success else R.drawable.error

        boxStrokeColor = ContextCompat.getColor(context, colorResId)
        setEndIconDrawable(iconResId)
        setEndIconTintList(ContextCompat.getColorStateList(context, colorResId))
    }

    private fun observeValidate() {
        binding.apply {
            val validations = listOf(
                registerVM.isValidName to nama,
                registerVM.isValidEmail to Email,
                registerVM.isValidNoTelp to telepon,
                registerVM.isValidPassword to Password
            )

            for ((validator, editText) in validations) {
                validator.observe(viewLifecycleOwner) {
                    editText.setValidationState(it)
                }
            }
        }
    }

    private fun observeValidateAll() {
        val validationMessages = listOf(
            "Nama tidak valid",
            "Email tidak valid",
            "Nomor telepon tidak valid",
            "Password min 8 karakter!"
        )

        val validators = listOf(
            registerVM.isValidName,
            registerVM.isValidEmail,
            registerVM.isValidNoTelp,
            registerVM.isValidPassword
        )

        var isFormValid = true

        for ((index, validator) in validators.withIndex()) {
            val validationMessage = validationMessages[index]

            val observer = Observer<Boolean> {
                if (!it) {
                    isFormValid = false
                    Toast(requireContext()).showCustomToast(validationMessage,
                        requireActivity(),
                        R.layout.toast_alert_red)
                }
            }

            validator.observe(viewLifecycleOwner, observer)
            validator.removeObserver(observer)
        }

        val fields = listOf(
            binding.masukanNama,
            binding.masukanEmail,
            binding.masukanTelepon,
            binding.masukanPassword
        )

        val isFieldsNotEmpty = fields.all { it.text.toString().isNotEmpty() }

        if (isFormValid && isFieldsNotEmpty) {
            Toast(requireContext()).showCustomToast("Form valid",
                requireActivity(),
                R.layout.toast_alert_green)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}