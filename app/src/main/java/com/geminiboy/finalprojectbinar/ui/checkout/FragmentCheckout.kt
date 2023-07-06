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
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentCheckoutBinding
import com.geminiboy.finalprojectbinar.ui.checkout.adapter.ItemCountPassengerAdapter
import com.geminiboy.finalprojectbinar.ui.checkout.adapter.ItemCountPassengerRoundTripAdapter
import com.geminiboy.finalprojectbinar.ui.home.HomeFragment
import com.geminiboy.finalprojectbinar.utils.Utils
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCheckout : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val checkoutVM: CheckoutViewModel by viewModels()
    private val passengerAdapter = ItemCountPassengerAdapter()
    private val passengerAdapterRoundTrip = ItemCountPassengerRoundTripAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            if(HomeFragment.isRoundTrip){
                initRoundTrip()
                containerReturn.visibility = View.VISIBLE
                rvPassenger.adapter = passengerAdapter
                rvPassengerReturn.adapter = passengerAdapterRoundTrip
                rvPassenger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvPassengerReturn.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }else{
                initDeparture()
                containerReturn.visibility = View.GONE
                rvPassenger.adapter = passengerAdapter
                rvPassenger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDeparture(){
        checkoutVM.apply {
            getTransactionId().observe(viewLifecycleOwner){
                Log.d("TRANSACTION_ID", it)
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
                                    tvTotalPriceBaby.text = "IDR 0"
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

                            if(data.paymentMethod != null){
                                btnBeranda.visibility = View.VISIBLE
                                btnLanjutPembayaran.visibility = View.GONE
                                tvInformationBody.visibility = View.GONE
                                rvPassenger.visibility = View.VISIBLE
                                passengerAdapter.setFilteredTickets(data.tickets, data.departureFlightId)
                                btnBeranda.setOnClickListener {
                                    findNavController().navigate(R.id.homeFragment)
                                }
                            }else{
                                btnBeranda.visibility = View.GONE
                                btnLanjutPembayaran.visibility = View.VISIBLE
                                tvInformationBody.visibility = View.VISIBLE
                                rvPassenger.visibility = View.GONE
                                tvInformationBody.text = data.departureFlight.description
                                btnLanjutPembayaran.setOnClickListener {
                                    val bundle = Bundle().apply {
                                        putInt("total_price", total)
                                    }
                                    findNavController().navigate(R.id.action_fragmentCheckout_to_paymentFragment, bundle)
                                }
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

    @SuppressLint("SetTextI18n")
    private fun initRoundTrip(){
        checkoutVM.apply {
            getTransactionId().observe(viewLifecycleOwner){
                Log.d("TRANSACTION_ID", it)
                getTransactionByIdRoundTrip(it)
            }

            getTransactionRoundTrip.observe(viewLifecycleOwner){ it ->
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
                            tvTimeArrive.text = Utils().formatTime(data.departureFlight.arrivalTime)
                            tvDateArrive.text = Utils().formatDate3(data.departureFlight.arrivalDate)
                            tvArriveAirport.text = data.departureFlight.destinationAirport.airportName

                            tvFlightDestinationReturn.text = "${data.returnFlight.originAirport.location} -> ${data.returnFlight.destinationAirport.location}"
                            tvFlightTimeReturn.text = "(${
                                Utils().formatDuration(
                                    Utils().formatTime(data.returnFlight.departureTime),
                                    Utils().formatTime(data.returnFlight.arrivalTime)
                                )
                            })"
                            tvTimeReturn.text = Utils().formatTime(data.returnFlight.departureTime)
                            tvDateReturn.text = Utils().formatDate3(data.returnFlight.departureDate)
                            tvReturnAirport.text = data.returnFlight.originAirport.airportName
                            tvNameAirlinesAndClassReturn.text = "${data.returnFlight.airline.airlineName} - ${data.returnFlight.classX}"
                            tvFlightNumberReturn.text = data.returnFlight.flightNumber
                            tvTimeArriveReturn.text = Utils().formatTime(data.returnFlight.arrivalTime)
                            tvDateArriveReturn.text = Utils().formatDate3(data.returnFlight.arrivalDate)
                            tvArriveAirportReturn.text = data.returnFlight.destinationAirport.airportName


                            getAdultCount().observe(viewLifecycleOwner){ it ->
                                if(it.toInt() < 1){
                                    layoutNumAdult.visibility = View.GONE
                                }else{
                                    layoutNumAdult.visibility = View.VISIBLE
                                    tvNumAdult.text = "$it Adult"
                                    getTotalPriceRoundTrip().observe(viewLifecycleOwner){ price ->
                                        tvTotalPriceAdult.text = "IDR ${Utils().formatCurrency(price * it.toInt())}"
                                    }
                                }
                            }

                            getChildCount().observe(viewLifecycleOwner){ it ->
                                if(it.toInt() < 1){
                                    layoutNumChild.visibility = View.GONE
                                }else{
                                    layoutNumChild.visibility = View.VISIBLE
                                    tvNumChild.text = "$it Child"
                                    getTotalPriceRoundTrip().observe(viewLifecycleOwner) { price ->
                                        tvTotalPriceChild.text =
                                            "IDR ${Utils().formatCurrency(price * it.toInt())}"
                                    }
                                }
                            }

                            getBabyCount().observe(viewLifecycleOwner){ it ->
                                if(it.toInt() < 1){
                                    layoutNumBaby.visibility = View.GONE
                                }else{
                                    layoutNumBaby.visibility = View.VISIBLE
                                    tvNumBaby.text = "$it Baby"
                                    tvTotalPriceBaby.text = "IDR 0"
                                }
                            }

                            var tax = 0
                            getPassenger().observe(viewLifecycleOwner){
                                tax = it.toInt() * 100000
                                tvTotalTax.text = "IDR ${Utils().formatCurrency(tax)}"
                            }

                            var total = 0
                            getPassengerAdultAndChild().observe(viewLifecycleOwner){
                                getTotalPriceRoundTrip().observe(viewLifecycleOwner) { price ->
                                    total = (price * it.toInt()) + tax
                                    tvTotalPrice.text = "IDR ${Utils().formatCurrency(total)}"
                                }
                            }

                            if(data.paymentMethod != null){
                                btnBeranda.visibility = View.VISIBLE
                                btnLanjutPembayaran.visibility = View.GONE
                                tvInformationBody.visibility = View.GONE
                                tvInformationBodyReturn.visibility = View.GONE
                                rvPassengerReturn.visibility = View.VISIBLE
                                rvPassenger.visibility = View.VISIBLE
                                passengerAdapterRoundTrip.setFilteredTickets(data.tickets, data.returnFlightId)
                                passengerAdapter.setFilteredTickets(data.tickets, data.departureFlightId)
                                btnBeranda.setOnClickListener {
                                    findNavController().navigate(R.id.homeFragment)
                                }
                            }else{
                                btnBeranda.visibility = View.GONE
                                btnLanjutPembayaran.visibility = View.VISIBLE
                                tvInformationBody.visibility = View.VISIBLE
                                tvInformationBodyReturn.visibility = View.VISIBLE
                                rvPassengerReturn.visibility = View.GONE
                                rvPassenger.visibility = View.GONE
                                tvInformationBodyReturn.text = data.returnFlight.description
                                tvInformationBody.text = data.departureFlight.description
                                btnLanjutPembayaran.setOnClickListener {
                                    findNavController().navigate(R.id.action_fragmentCheckout_to_paymentFragment)
                                }
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