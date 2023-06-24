package com.geminiboy.finalprojectbinar.ui.otp

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentOtpBinding
import com.geminiboy.finalprojectbinar.model.otp.OtpBody
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private val otpVM: OtpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
        setAutomaticMoveEditText()
        observeOtp()
        val getEmail = arguments?.getString("email")
        binding.tvEmail.text = getEmail
    }

    private fun setAutomaticMoveEditText(){
        binding.apply {
            val editTextList = listOf(kode1, kode2, kode3, kode4, kode5, kode6)

            editTextList.forEachIndexed { index, editText ->
                editText.addTextChangedListener {
                    if (it != null && it.length == 1) {
                        val nextIndex = index + 1
                        if (nextIndex < editTextList.size) {
                            editTextList[nextIndex].requestFocus()
                        } else {
                            val otpValue = editTextList.joinToString("") { editText -> editText.text.toString().trim() }
                                .replace("\\p{C}".toRegex(), "")
                            Log.d("OTEPEEE", otpValue)
                            otpVM.postOTP(OtpBody(otpValue))
                        }
                    }
                }

                editText.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN && editText.text!!.isEmpty()) {
                        val previousIndex = index - 1
                        if (previousIndex >= 0) {
                            editTextList[previousIndex].apply {
                                requestFocus()
                                setText("")
                            }
                        } else {
                            editText.setText("")
                        }
                        return@setOnKeyListener true
                    }
                    false
                }
            }

        }
    }

    private fun observeOtp(){
        otpVM.otp.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast(requireContext()).showCustomToast(
                        "Registrasi Berhasil",
                        requireActivity(),
                        R.layout.toast_alert_green
                    )
                    lifecycleScope.launch { delay(500) }
                    findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
                }
                is Resource.Error -> {
                    Toast(requireContext()).showCustomToast(
                        "Maaf, Kode OTP Salah.",
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