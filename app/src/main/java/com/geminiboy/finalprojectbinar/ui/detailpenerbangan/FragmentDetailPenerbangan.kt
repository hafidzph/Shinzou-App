package com.geminiboy.finalprojectbinar.ui.detailpenerbangan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentDetailPenerbanganBinding
import com.geminiboy.finalprojectbinar.ui.bottomsheet.login.LoginRequiredSheet
import com.geminiboy.finalprojectbinar.ui.home.HomeFragment
import com.geminiboy.finalprojectbinar.utils.Utils
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class FragmentDetailPenerbangan : Fragment() {
    private var _binding: FragmentDetailPenerbanganBinding? = null
    private val binding get() = _binding!!
    private val detailVM: DetailPenerbanganViewModel by viewModels()

    companion object{
        var isDetail = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPenerbanganBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeIsLoggedIn()
        observeDepartureId()
        isDetail = true
        LoginRequiredSheet.hasNavigatedToLogin = false
        binding.apply {
            if(HomeFragment.isRoundTrip){
                containerReturn.visibility = View.VISIBLE
                observeReturnId()
                observeTotalPriceRoundTrip()
                btnBack.setOnClickListener {
                    findNavController().navigate(R.id.action_fragmentDetailPenerbangan_to_searchResultRoundTripFragment)
                }
            }else{
                containerReturn.visibility = View.GONE
                btnBack.setOnClickListener {
                    findNavController().navigate(R.id.action_fragmentDetailPenerbangan_to_searchResultFragment)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeTotalPriceRoundTrip(){
        detailVM.getTotalPriceRoundTrip().observe(viewLifecycleOwner){
            val price = Utils().formatCurrency(it)
            binding.tvPriceTicket.text = "IDR $price/pax"
            detailVM.setTicketPrice(it.toString())
        }
    }

    private fun observeDepartureId(){
        detailVM.getDepartureId().observe(viewLifecycleOwner){
            Log.d("DEPARTURE ID", it)
            initDeparture(it)
        }
    }

    private fun observeReturnId(){
        detailVM.getReturnId().observe(viewLifecycleOwner){
            Log.d("RETURN ID", it)
            initReturn(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDeparture(id: String){
        detailVM.getFlightByIdDeparture(id)
        detailVM.detailFlightDeparture.observe(viewLifecycleOwner){
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
                        if(!HomeFragment.isRoundTrip){
                            val price = Utils().formatCurrency(data.price)
                            tvPriceTicket.text = "IDR $price/pax"
                            detailVM.setTicketPrice(data.price.toString())
                        }
                    }
                }
                is Resource.Error -> {}
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initReturn(id: String){
        detailVM.getFlightByIdReturn(id)
        detailVM.detailFlightReturn.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val data = it.data!!.data
                    binding.apply {
                        tvFlightDestinationReturn.text = "${data.originAirport.location} -> ${data.destinationAirport.location}"
                        tvFlightTimeReturn.text = "(${Utils().formatDuration(Utils().formatTime(data.departureTime), 
                            Utils().formatTime(data.arrivalTime))})"
                        tvTimeReturn.text = Utils().formatTime(data.departureTime)
                        tvDateReturn.text = Utils().formatDate3(data.departureDate)
                        tvAirportReturn.text = data.originAirport.airportName
                        tvNameAirlineAndClassReturn.text = "${data.airline.airlineName} - ${data.classX}"
                        tvFlightNumberReturn.text = data.flightNumber
                        tvInformationBodyReturn.text = data.description
                        tvTimeArriveReturn.text = Utils().formatTime(data.arrivalTime)
                        tvDateArriveReturn.text = Utils().formatDate3(data.arrivalDate)
                        tvArriveAirportReturn.text = data.destinationAirport.airportName
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
        if (!LoginRequiredSheet.hasNavigatedToLogin) {
            isDetail = false
        }
    }
}