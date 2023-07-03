package com.geminiboy.finalprojectbinar.ui.payment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentPaymentBinding
import com.geminiboy.finalprojectbinar.model.payment.PaymentBody
import com.geminiboy.finalprojectbinar.ui.bottomsheet.paymentsuccess.BottomSheetPaymentSuccessFragment
import com.geminiboy.finalprojectbinar.utils.Utils
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val paymentVM: PaymentViewModel by viewModels()
    private var booking_code = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
         cvGopay.setOnClickListener {
             if (expandLayoutGopay.visibility == View.GONE){
                 TransitionManager.beginDelayedTransition(cvGopay,AutoTransition())
                 expandLayoutGopay.visibility = View.VISIBLE
                 constraintLay.setBackgroundColor(resources.getColor(R.color.DARKBLUE05))
                 ivArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
             }else{
                TransitionManager.beginDelayedTransition(cvGopay, AutoTransition())
                 expandLayoutGopay.visibility = View.GONE
                 ivArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                 constraintLay.setBackgroundColor(resources.getColor(R.color.NEUTRAL04))
             }
         }

            cvCreditCard.setOnClickListener {
                if (expandLayoutCreditCard.visibility ==View.GONE){
                    TransitionManager.beginDelayedTransition(cvCreditCard,AutoTransition())
                    expandLayoutCreditCard.visibility = View.VISIBLE
                    constraintLay2.setBackgroundColor(resources.getColor(R.color.DARKBLUE05))
                    ivArrow2.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }else{
                    TransitionManager.beginDelayedTransition(cvCreditCard,AutoTransition())
                    expandLayoutCreditCard.visibility = View.GONE
                    constraintLay2.setBackgroundColor(resources.getColor(R.color.NEUTRAL04))
                    ivArrow2.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }

            btnBayarGopay.setOnClickListener {
                addPayment(PaymentBody("Gopay"))
                BottomSheetPaymentSuccessFragment().show(requireActivity().supportFragmentManager,BottomSheetPaymentSuccessFragment.bottomTag)
            }
            btnBayarCreditCard.setOnClickListener {
                addPayment(PaymentBody("Credit Card"))
                BottomSheetPaymentSuccessFragment().show(requireActivity().supportFragmentManager,BottomSheetPaymentSuccessFragment.bottomTag)
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        setPassenger()
        val getTotal = arguments?.getInt("total_price")!!
        observeTransaction(getTotal)
    }

    private fun addPayment(paymentBody: PaymentBody){
        paymentVM.addPayment(booking_code, paymentBody)
        paymentVM.addPayment.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    BottomSheetPaymentSuccessFragment().show(requireActivity().supportFragmentManager, "paymentSuccessTAG")
                }
                is Resource.Error -> {
                    Toast(requireContext()).showCustomToast(
                        "Pembayaran Gagal!!",
                        requireActivity(),
                        R.layout.toast_alert_red
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeTransaction(totalHarga: Int){
        paymentVM.apply {
            getTransactionId().observe(viewLifecycleOwner){
                getTransactionById(it)
            }

            getTransaction.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        binding.apply {
                            val data = it.data!!.data
                            tvDeparture.text = data.departureFlight.originAirport.location
                            tvTanggal.text = Utils().formatDate3(data.departureFlight.departureDate)
                            tvJam.text = Utils().formatTime(data.departureFlight.departureTime)
                            tvDepartureDua.text = data.departureFlight.destinationAirport.location
                            tvTanggalDua.text = Utils().formatDate3(data.departureFlight.arrivalDate)
                            tvJamDua.text = Utils().formatTime(data.departureFlight.arrivalTime)
                            noBookingCode.text = data.bookingCode
                            typeClass.text = data.departureFlight.classX
                            tvHarga.text = "IDR ${Utils().formatCurrency(totalHarga)}"
                            booking_code = data.bookingCode
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun setPassenger(){
        paymentVM.updatePassengerCount()
        paymentVM.getPassengerCount().observe(viewLifecycleOwner){(adult, child, baby) ->
            val passengerText = buildString {
                if (adult > 0) {
                    append("$adult Adult")
                }
                if (child > 0) {
                    if (isNotEmpty()) {
                        append(", ")
                    }
                    append("$child Child")
                }
                if (baby > 0) {
                    if (isNotEmpty()) {
                        append(", ")
                    }
                    append("$baby Baby")
                }
            }
            binding.tvTypePassengersCount.text = passengerText
        }

    }
}