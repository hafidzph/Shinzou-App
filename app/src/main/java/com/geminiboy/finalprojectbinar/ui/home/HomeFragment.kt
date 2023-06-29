package com.geminiboy.finalprojectbinar.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentHomeBinding
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.ui.bottomsheet.choosedate.SetDateSheet
import com.geminiboy.finalprojectbinar.ui.bottomsheet.searchingdestination.SearchingDestinationSheet
import com.geminiboy.finalprojectbinar.ui.bottomsheet.setclass.SetClassSheet
import com.geminiboy.finalprojectbinar.ui.bottomsheet.setpassenger.SetPassengerSheet
import com.geminiboy.finalprojectbinar.ui.home.adapter.FavouriteDestinationAdapter
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeVM: HomeViewModel by viewModels()
    private val favouriteDestinationAdapter: FavouriteDestinationAdapter = FavouriteDestinationAdapter()

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)

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

            containerFrom.setOnClickListener {
                SearchingDestinationSheet(true).show(requireActivity().supportFragmentManager, "searchingTag")
            }

            containerTo.setOnClickListener {
                SearchingDestinationSheet(false).show(requireActivity().supportFragmentManager, "searchingTag")
            }

            btnCari.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchResultFragment)
            }

            btnSwitch.setOnClickListener {
                val temp = tvDestinationFrom.text
                tvDestinationFrom.text = tvDestinationTo.text
                tvDestinationTo.text = temp
            }
        }

        setPassenger()
        setSeatClass()
        setDateDeparture()
        setDateReturn()
        observeFavDestination()
        setTextFrom()
    }

    @SuppressLint("SetTextI18n")
    private fun setTextFrom(){
        binding.apply {
            homeVM.getFrom().observe(viewLifecycleOwner){
                if (it.contains("[A-Za-z]".toRegex())) tvDestinationFrom.text = it
            }

            homeVM.getTo().observe(viewLifecycleOwner){
                if (it.contains("[A-Za-z]".toRegex())) tvDestinationTo.text = it
            }
        }
    }

    private fun observeFavDestination(){
        homeVM.getFlight()
        homeVM.favDestination.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.apply {
                        rvDestinasiFav.visibility = View.GONE
                        destinationShimmer.visibility = View.VISIBLE
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        rvDestinasiFav.visibility = View.VISIBLE
                        destinationShimmer.visibility = View.GONE
                        favouriteDestinationAdapter.submitData(it.data?.data!!)
                        rvDestinasiFav.adapter = favouriteDestinationAdapter
                        rvDestinasiFav.layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false)
                    }
                }
                is Resource.Error -> {
                    Toast(requireContext()).showCustomToast(
                        it.message!!,
                        requireActivity(),
                        R.layout.toast_alert_red
                    )
                    Log.d("error destination", it.message)
                }
            }
        }
    }

    private fun setDateDeparture(){
        homeVM.getDateDeparture().observe(viewLifecycleOwner){
            if(it != ""){
                binding.apply {
                    tvDeparture.text = it.substringAfter(", ")
                    tvDeparture.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL05))
                }
            }
        }
    }

    private fun setDateReturn(){
        homeVM.getDateReturn().observe(viewLifecycleOwner){
            if(it != ""){
                binding.apply {
                    tvReturn.text = if(it != "-") it.substringAfter(", ") else "-"
                    tvReturn.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL05))
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setPassenger(){
        homeVM.getPassenger().observe(viewLifecycleOwner){
            if(it != 0){
                binding.countPassenger.text = "$it Penumpang"
                binding.countPassenger.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL05))
            }
        }
    }

    private fun setSeatClass(){
        homeVM.getSeatClass().observe(viewLifecycleOwner){
            if(it != ""){
                binding.tvSeatClass.text = it
                binding.tvSeatClass.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL05))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}