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

    private fun observeValidate(){
        binding.apply {
            registerVM.isValidName.observe(viewLifecycleOwner){
                nama.setValidationState(it)
            }

            registerVM.isValidEmail.observe(viewLifecycleOwner){
                Email.setValidationState(it)
            }

            registerVM.isValidNoTelp.observe(viewLifecycleOwner){
                telepon.setValidationState(it)
            }

            registerVM.isValidPassword.observe(viewLifecycleOwner){
                Password.setValidationState(it)
            }
        }
    }

    private fun observeValidateAll(){
        var isFormValid = true
        val nameObserver = Observer<Boolean> {
            if (!it) {
                isFormValid = false
                Toast(requireContext()).showCustomToast("Nama tidak valid",
                    requireActivity(),
                    R.layout.toast_alert_red)
            }
        }

        val emailObserver = Observer<Boolean> {
            if (!it) {
                isFormValid = false
                Toast(requireContext()).showCustomToast("Email tidak valid",
                    requireActivity(),
                    R.layout.toast_alert_red)
            }
        }

        val phoneNumberObserver = Observer<Boolean> {
            if (!it) {
                isFormValid = false
                Toast(requireContext()).showCustomToast("Nomor telepon tidak valid",
                    requireActivity(),
                    R.layout.toast_alert_red)
            }
        }

        val passwordObserver = Observer<Boolean> {
            if (!it) {
                isFormValid = false
                Toast(requireContext()).showCustomToast("Password min 8 karakter!",
                    requireActivity(),
                    R.layout.toast_alert_red)
            }
        }

        registerVM.isValidName.observe(viewLifecycleOwner, nameObserver)
        registerVM.isValidEmail.observe(viewLifecycleOwner, emailObserver)
        registerVM.isValidNoTelp.observe(viewLifecycleOwner, phoneNumberObserver)
        registerVM.isValidPassword.observe(viewLifecycleOwner, passwordObserver)

        registerVM.isValidName.removeObserver(nameObserver)
        registerVM.isValidEmail.removeObserver(emailObserver)
        registerVM.isValidNoTelp.removeObserver(phoneNumberObserver)
        registerVM.isValidPassword.removeObserver(passwordObserver)

        if (isFormValid && binding.masukanNama.text.toString().isNotEmpty() &&
                binding.masukanEmail.text.toString().isNotEmpty() &&
                binding.masukanTelepon.text.toString().isNotEmpty() &&
                binding.masukanPassword.text.toString().isNotEmpty()) {
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