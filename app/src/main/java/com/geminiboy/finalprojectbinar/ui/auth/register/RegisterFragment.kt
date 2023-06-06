package com.geminiboy.finalprojectbinar.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.utils.showCustomToast

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
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
            btnDaftar.setOnClickListener {
                Toast(requireContext()).showCustomToast("Password minimal 8 karakter!", requireActivity(), R.layout.toast_alert_red)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}