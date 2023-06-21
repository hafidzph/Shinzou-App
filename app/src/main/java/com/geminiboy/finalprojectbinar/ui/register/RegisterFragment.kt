package com.geminiboy.finalprojectbinar.ui.register

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
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

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
        val color = ContextCompat.getColor(context, colorResId)
        val colorState = ContextCompat.getColorStateList(context, colorResId)

        boxStrokeColor = color
        setEndIconDrawable(iconResId)
        setEndIconTintList(colorState)
    }

    private fun observeValidate() {
        binding.apply {
            val validations = listOf(
                registerVM.isValidName to nama,
                registerVM.isValidEmail to Email,
                registerVM.isValidNoTelp to telepon,
                registerVM.isValidPassword to Password
            )

            for ((validator, textInputLayout) in validations) {
                validator.observe(viewLifecycleOwner) {
                    if (!it) {
                        textInputLayout.error = " "
                    }
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
        val fields = listOf(
            binding.masukanNama,
            binding.masukanEmail,
            binding.masukanTelepon,
            binding.masukanPassword
        )

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

        val isFieldsNotEmpty = fields.all { it.text.toString().isNotEmpty() }

        if (isFormValid && isFieldsNotEmpty) {
            val name = binding.masukanNama.text.toString()
            val email = binding.masukanEmail.text.toString()
            val nomorTelepon = binding.masukanTelepon.text.toString()
            val password = binding.masukanPassword.text.toString()
            registerVM.postUser(RegisterBody(email, name, password, nomorTelepon))
            observe()
        } else {
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

    fun observe(){
        registerVM.userRegister.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast(requireContext()).showCustomToast(
                        "Register successfull!",
                        requireActivity(),
                        R.layout.toast_alert_green
                    )
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Resource.Error -> {
                    val errorMessage = resource.message
                    Toast(requireContext()).showCustomToast(
                        errorMessage!!,
                        requireActivity(),
                        R.layout.toast_alert_red
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}