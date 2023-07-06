package com.geminiboy.finalprojectbinar.ui.bottomsheet.paymentsuccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentBottomSheetPaymentSuccessBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetPaymentSuccessFragment : BottomSheetDialogFragment() {
    lateinit var binding:FragmentBottomSheetPaymentSuccessBinding

    companion object{
        val bottomTag : String = "paymentSuccess"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetPaymentSuccessBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            icClose.setOnClickListener {
                dismiss()
            }

            btnTerbitkanTiket.setOnClickListener {
                findNavController().navigate(R.id.fragmentCheckout)
                dismiss()
            }
        }
    }


}