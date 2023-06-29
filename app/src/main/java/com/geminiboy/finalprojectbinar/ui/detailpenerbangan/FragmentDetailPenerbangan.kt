package com.geminiboy.finalprojectbinar.ui.detailpenerbangan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentDetailPenerbanganBinding
import com.geminiboy.finalprojectbinar.ui.bottomsheet.login.LoginRequiredSheet
import com.geminiboy.finalprojectbinar.utils.Utils
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDetailPenerbangan : Fragment() {
    private var _binding: FragmentDetailPenerbanganBinding? = null
    private val binding get() = _binding!!
    private val detailVM: DetailPenerbanganViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPenerbanganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getId = arguments?.getString("id")!!
        init(getId)
        observeIsLoggedIn()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init(id: String){
        detailVM.getFlightById(id)
        detailVM.detailFlight.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val data = it.data!!.data
                    binding.apply {
                        tvFlightDestination.text = "${data.originAirport.location} -> ${data.destinationAirport.location}"
                        tvFlightTime.text = "(${Utils().formatDuration(Utils().formatTime(data.departureTime), 
                            Utils().formatTime(data.arrivalTime))})"
                        tvTimeDeparture.text = Utils().formatTime(data.departureTime)
                        tvDateDeparture.text = Utils().formatDate3(data.departureDate)
                        tvDepartureAirport.text = data.originAirport.airportName
                        tvNameAirlineAndClass.text = "${data.airline.airlineName} - ${data.classX}"
                        tvFlightNumber.text = data.flightNumber
                        tvInformationBody.text = data.description
                        tvTimeArrive.text = Utils().formatTime(data.arrivalTime)
                        tvDateArrive.text = Utils().formatDate3(data.arrivalDate)
                        tvArriveAirport.text = data.destinationAirport.airportName
                    }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun observeIsLoggedIn(){
        binding.btnSelectFlight.setOnClickListener {
            detailVM.getToken().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    findNavController().navigate(R.id.action_fragmentDetailPenerbangan_to_fragmentBiodataPemesan)
                } else {
                    LoginRequiredSheet().show(
                        requireActivity().supportFragmentManager,
                        "loginRequiredTAG"
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}