package com.geminiboy.finalprojectbinar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentHomeBinding
import com.geminiboy.finalprojectbinar.ui.bottomsheet.choosedate.SetDateSheet
import com.geminiboy.finalprojectbinar.ui.bottomsheet.setclass.SetClassSheet
import com.geminiboy.finalprojectbinar.ui.bottomsheet.setpassenger.SetPassengerSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object{
        var isRoundTrip = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            containerPassenger.setOnClickListener {
                SetPassengerSheet().show(requireActivity().supportFragmentManager, "passengerTag")
            }

            containerContentDeparture.setOnClickListener {
                SetDateSheet().show(requireActivity().supportFragmentManager, "dateTag")
            }

            containerContentSeat.setOnClickListener {
                SetClassSheet().show(requireActivity().supportFragmentManager, "setClassTag")
            }

            switchToggle.setOnCheckedChangeListener{_, isChecked ->
                isRoundTrip = isChecked
            }
        }
    }
}