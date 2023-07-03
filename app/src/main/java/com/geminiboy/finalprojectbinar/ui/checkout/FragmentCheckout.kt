package com.geminiboy.finalprojectbinar.ui.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentCheckoutBinding
import com.geminiboy.finalprojectbinar.utils.Utils
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCheckout : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val checkoutVM: CheckoutViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        checkoutVM.apply {
            getTransactionId().observe(viewLifecycleOwner){
               getTransactionById(it)
            }

            getTransaction.observe(viewLifecycleOwner){ it ->
                when(it){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val data = it.data!!.data
                        binding.apply {
                            tvFlightDestination.text = "${data.departureFlight.originAirport.location} -> ${data.departureFlight.destinationAirport.location}"
                            tvFlightTime.text = "(${
                                Utils().formatDuration(
                                    Utils().formatTime(data.departureFlight.departureTime),
                                    Utils().formatTime(data.departureFlight.arrivalTime)
                                )
                            })"
                            tvTimeDeparture.text = Utils().formatTime(data.departureFlight.departureTime)
                            tvDateDeparture.text = Utils().formatDate3(data.departureFlight.departureDate)
                            tvDepartureAirport.text = data.departureFlight.originAirport.airportName
                            tvNameAirlinesAndClass.text = "${data.departureFlight.airline.airlineName} - ${data.departureFlight.classX}"
                            tvFlightNumber.text = data.departureFlight.flightNumber
                            tvInformationBody.text = data.departureFlight.description
                            tvTimeArrive.text = Utils().formatTime(data.departureFlight.arrivalTime)
                            tvDateArrive.text = Utils().formatDate3(data.departureFlight.arrivalDate)
                            tvArriveAirport.text = data.departureFlight.destinationAirport.airportName
                            checkoutVM.getAdultCount().observe(viewLifecycleOwner){ it ->
                                if(it.toInt() < 1){
                                    layoutNumAdult.visibility = View.GONE
                                }else{
                                    layoutNumAdult.visibility = View.VISIBLE
                                    tvNumAdult.text = "$it Adult"
                                    tvTotalPriceAdult.text = "IDR ${Utils().formatCurrency(data.departureFlight.price * it.toInt())}"
                                }
                            }

                            checkoutVM.getChildCount().observe(viewLifecycleOwner){ it ->
                                if(it.toInt() < 1){
                                    layoutNumChild.visibility = View.GONE
                                }else{
                                    layoutNumChild.visibility = View.VISIBLE
                                    tvNumChild.text = "$it Child"
                                    tvTotalPriceChild.text = "IDR ${Utils().formatCurrency(data.departureFlight.price * it.toInt())}"
                                }
                            }

                            checkoutVM.getBabyCount().observe(viewLifecycleOwner){ it ->
                                if(it.toInt() < 1){
                                    layoutNumBaby.visibility = View.GONE
                                }else{
                                    layoutNumBaby.visibility = View.VISIBLE
                                    tvNumBaby.text = "$it Baby"
                                    tvNumBaby.text = "IDR 0"
                                }
                            }
                            var tax = 0
                            checkoutVM.getPassenger().observe(viewLifecycleOwner){
                                tax = it.toInt() * 100000
                                tvTotalTax.text = "IDR ${Utils().formatCurrency(tax)}"
                            }

                            var total = 0
                            checkoutVM.getPassengerAdultAndChild().observe(viewLifecycleOwner){
                                total = (data.departureFlight.price * it.toInt()) + tax
                                tvTotalPrice.text = "IDR ${Utils().formatCurrency(total)}"
                            }

                            btnLanjutPembayaran.setOnClickListener {
                                val bundle = Bundle().apply {
                                    putInt("total_price", total)
                                }
                                findNavController().navigate(R.id.action_fragmentCheckout_to_paymentFragment, bundle)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.d("Error Checkout", it.message!!)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}