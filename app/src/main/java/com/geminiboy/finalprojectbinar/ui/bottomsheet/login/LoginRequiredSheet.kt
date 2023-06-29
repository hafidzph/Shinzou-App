package com.geminiboy.finalprojectbinar.ui.bottomsheet.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentLoginRequiredSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginRequiredSheet : BottomSheetDialogFragment(){
    private var _binding: FragmentLoginRequiredSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginRequiredSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            btnToLogin.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}