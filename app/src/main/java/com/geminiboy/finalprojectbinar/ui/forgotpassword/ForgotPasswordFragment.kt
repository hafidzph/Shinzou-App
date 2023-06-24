package com.geminiboy.finalprojectbinar.ui.forgotpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentForgotPasswordBinding
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordBody
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val forgotVM: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        postForgotPassword()
        observe()
    }

    private fun postForgotPassword(){
        binding.btnKirim.setOnClickListener {
            forgotVM.forgotPassword(ForgotPasswordBody(binding.etEmail.text.toString()))
        }
    }

    private fun observe(){
        forgotVM.forgotPassword.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast(requireContext()).showCustomToast(
                        resource.data?.message!!,
                        requireActivity(),
                        R.layout.toast_alert_green
                    )
                }

                is Resource.Error -> {
                    val errorMessage = resource.message!!
                    when (extractErrorCodeFromMessage(errorMessage)) {
                        "404" -> {
                            Toast(requireContext()).showCustomToast(
                                "Alamat email tidak ditemukan.",
                                requireActivity(),
                                R.layout.toast_alert_red
                            )
                        }

                        "422" -> {
                            Toast(requireContext()).showCustomToast(
                                "Email wajib diisi.",
                                requireActivity(),
                                R.layout.toast_alert_red
                            )
                        }
                    }
                }
            }
        }
    }

    private fun extractErrorCodeFromMessage(errorMessage: String): String {
        return when {
            errorMessage.contains("404") -> "404"
            errorMessage.contains("422") -> "422"
            else -> "Unknown"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}