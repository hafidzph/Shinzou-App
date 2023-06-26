package com.geminiboy.finalprojectbinar.ui.payment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentPaymentBinding
import com.geminiboy.finalprojectbinar.ui.bottomsheet.paymentsuccess.BottomSheetPaymentSuccessFragment

class PaymentFragment : Fragment() {

    lateinit var binding : FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater,container,false)
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
                BottomSheetPaymentSuccessFragment().show(requireActivity().supportFragmentManager,BottomSheetPaymentSuccessFragment.bottomTag)
            }
            btnBayarCreditCard.setOnClickListener {
                BottomSheetPaymentSuccessFragment().show(requireActivity().supportFragmentManager,BottomSheetPaymentSuccessFragment.bottomTag)
            }
        }
    }



}