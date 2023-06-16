package com.geminiboy.finalprojectbinar.ui.bottomsheet.setpassenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSetPassengerSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetPassengerSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSetPassengerSheetBinding? = null
    private val binding get() = _binding!!
    private val setPassengerVM: SetPassengerViewModel by viewModels()
    private var dewasaCount = 0
    private var anakCount = 0
    private var bayiCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSetPassengerSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            btnSimpan.setOnClickListener {
                setPassengerVM.apply {
                    setAdultPassenger(etDewasa.text.toString())
                    setBabyPassenger(etBayi.text.toString())
                    setChildrenPassenger(etAnak.text.toString())
                    lifecycleScope.launch {
                        delay(500)
                        dismiss()
                    }
                }
            }
        }
        setDewasaCount()
        setAnakCount()
        setBayiCount()
    }

    private fun setDewasaCount(){
        binding.apply {
            btnMinDewasa.setOnClickListener {
                if (dewasaCount > 0) {
                    dewasaCount--
                    binding.etDewasa.setText(dewasaCount.toString())
                }
            }

            btnPlusDewasa.setOnClickListener {
                dewasaCount++
                binding.etDewasa.setText(dewasaCount.toString())
            }
        }
    }

    private fun setAnakCount(){
        binding.apply {
            btnMinAnak.setOnClickListener {
                if (anakCount > 0) {
                    anakCount--
                    binding.etAnak.setText(anakCount.toString())
                }
            }

            btnPlusAnak.setOnClickListener {
                anakCount++
                binding.etAnak.setText(anakCount.toString())
            }
        }
    }

    private fun setBayiCount(){
        binding.apply {
            btnMinBayi.setOnClickListener {
                if (bayiCount > 0) {
                    bayiCount--
                    binding.etBayi.setText(bayiCount.toString())
                }
            }

            btnPlusBayi.setOnClickListener {
                bayiCount++
                binding.etBayi.setText(bayiCount.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}