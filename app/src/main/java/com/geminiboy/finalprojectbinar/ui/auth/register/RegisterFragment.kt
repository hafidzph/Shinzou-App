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
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.utils.showCustomToast

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

            registerVM.isValid.observe(viewLifecycleOwner){
                if(it){
                    nama.apply {
                        boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.Green)
                        setEndIconDrawable(R.drawable.success)
                        setEndIconTintList(ContextCompat.getColorStateList(requireContext(), R.color.Green))
                    }
                }else{
                    nama.apply {
                        boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.Red)
                        setEndIconDrawable(R.drawable.error)
                        setEndIconTintList(ContextCompat.getColorStateList(requireContext(), R.color.Red))
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