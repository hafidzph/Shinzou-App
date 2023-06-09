package com.geminiboy.finalprojectbinar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentHomeBinding
import com.geminiboy.finalprojectbinar.ui.bottomsheet.setpassenger.SetPassengerSheet

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutDetailContentShimmer.startShimmer()

        binding.containerPassenger.setOnClickListener {
            SetPassengerSheet().show(requireActivity().supportFragmentManager, "passengerTag")
        }
    }
}