package com.geminiboy.finalprojectbinar.ui.bottomsheet.setclass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSetClassSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetClassSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSetClassSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetClassSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnCLoseSetKelas.setOnClickListener{
                dismiss()
            }
            relativeEconomy.setOnClickListener { selectSeatClass(0) }
            relativePremiumEconomy.setOnClickListener { selectSeatClass(1) }
            relativeBusiness.setOnClickListener { selectSeatClass(2) }
            relativeFirstClass.setOnClickListener { selectSeatClass(3) }
        }
    }

    private fun selectSeatClass(selectedIndex: Int) {
        val seatClassList = listOf(
            binding.relativeEconomy,
            binding.relativePremiumEconomy,
            binding.relativeBusiness,
            binding.relativeFirstClass
        )

        seatClassList.forEachIndexed { index, seatClass ->
            val isSelected = (index == selectedIndex)
            val textColorRes = if (isSelected) R.color.white else R.color.NEUTRAL05
            val backgroundColorRes = if (isSelected) R.color.DARKBLUE04 else R.color.white

            seatClass.setBackgroundColor(resources.getColor(backgroundColorRes))
            seatClass.findViewById<MaterialTextView>(getSeatClassTextViewId(index)).setTextColor(resources.getColor(textColorRes))
            seatClass.findViewById<MaterialTextView>(getSeatClassHargaTextViewId(index)).setTextColor(resources.getColor(textColorRes))
            seatClass.findViewById<ShapeableImageView>(getSeatClassSuccessImageViewId(index)).visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }

    private fun getSeatClassTextViewId(index: Int): Int {
        return when (index) {
            0 -> R.id.tvEconomy
            1 -> R.id.tvPremiumeconomy
            2 -> R.id.tvBusiness
            3 -> R.id.tvFirstClass
            else -> throw IllegalArgumentException("Invalid seat class index")
        }
    }

    private fun getSeatClassHargaTextViewId(index: Int): Int {
        return when (index) {
            0 -> R.id.tvHargaEconomy
            1 -> R.id.tvHargaPremiumEconomy
            2 -> R.id.tvHargaBusiness
            3 -> R.id.tvHargaFirstClass
            else -> throw IllegalArgumentException("Invalid seat class index")
        }
    }

    private fun getSeatClassSuccessImageViewId(index: Int): Int {
        return when (index) {
            0 -> R.id.successEconomy
            1 -> R.id.successPremiumEconomy
            2 -> R.id.successBusiness
            3 -> R.id.successFirstClass
            else -> throw IllegalArgumentException("Invalid seat class index")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}