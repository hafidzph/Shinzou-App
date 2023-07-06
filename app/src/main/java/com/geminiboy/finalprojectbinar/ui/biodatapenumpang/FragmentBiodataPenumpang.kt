package com.geminiboy.finalprojectbinar.ui.biodatapenumpang

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentBiodataPenumpangBinding
import com.geminiboy.finalprojectbinar.model.flight.TransactionBody
import com.geminiboy.finalprojectbinar.ui.biodatapenumpang.adapter.BiodataPenumpangAdapter
import com.geminiboy.finalprojectbinar.ui.home.HomeFragment
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentBiodataPenumpang : Fragment() {
    private var _binding: FragmentBiodataPenumpangBinding? = null
    private val binding get() = _binding!!
    private val biodataVM: BiodataPenumpangViewModel by viewModels()
    private val bioAdapter = BiodataPenumpangAdapter()
    var babyCount: Int = 0
    var childrenCount: Int = 0
    var adultCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBiodataPenumpangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValueAdapter()
        init()
        if(HomeFragment.isRoundTrip){
            postTransactionRoundTrip()
        }else{
            postTransactionDeparture()
        }
        observeTransaction()
    }

    private fun postTransactionDeparture(){
        binding.apply {
            btnSimpan.setOnClickListener {
                biodataVM.getDeparture().observe(viewLifecycleOwner) {id ->
                    biodataVM.getPrice().observe(viewLifecycleOwner) {
                        biodataVM.postTransaction(
                            TransactionBody(
                                id,
                                null,
                                bioAdapter.getPassengers(),
                                it!!.toInt()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun postTransactionRoundTrip(){
        binding.apply {
            btnSimpan.setOnClickListener {
                biodataVM.getDeparture().observe(viewLifecycleOwner) {id ->
                    biodataVM.getReturn().observe(viewLifecycleOwner) { returnId ->
                        biodataVM.getPrice().observe(viewLifecycleOwner) {
                            biodataVM.postTransaction(
                                TransactionBody(
                                    id,
                                    returnId,
                                    bioAdapter.getPassengers(),
                                    it!!.toInt()
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeTransaction(){
        biodataVM.addTransaction.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val data = it.data?.data
                    biodataVM.setTransactionId(data!!.id)
                    findNavController().navigate(R.id.action_fragmentBiodataPenumpang_to_fragmentCheckout)
                }
                is Resource.Error -> {
                    Log.d("error_transaction", it.data?.message ?: "Unknown error occurred")
                }
            }
        }
    }

    private fun init(){
        binding.apply {
            rvBioPassenger.adapter = bioAdapter
            rvBioPassenger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun initValueAdapter() {
        biodataVM.getBaby().observe(viewLifecycleOwner) { baby ->
            babyCount = baby.toInt()
            updateAdapterData()
        }
        biodataVM.getChildren().observe(viewLifecycleOwner) { children ->
            childrenCount = children.toInt()
            updateAdapterData()
        }
        biodataVM.getAdult().observe(viewLifecycleOwner) { adult ->
            adultCount = adult.toInt()
            updateAdapterData()
        }
    }

    private fun updateAdapterData() {
        bioAdapter.submitData(babyCount, childrenCount, adultCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}