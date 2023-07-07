package com.geminiboy.finalprojectbinar.ui.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentRiwayatBinding
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.ui.detailpenerbangan.FragmentDetailPenerbangan
import com.geminiboy.finalprojectbinar.ui.riwayat.adapter.RiwayatAdapter
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRiwayat : Fragment() {
    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private val riwayatVM: RiwayatViewModel by viewModels()
    private val riwayatAdapter = RiwayatAdapter()

    companion object{
        var isRiwayat = false
        var isRiwayatCheckout = false
        var hasNavigatedToLogin = false
        var hasNavigatedToCheckOut = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        observeIsLoggedIn()
        isRiwayat = true
        isRiwayatCheckout = true
        hasNavigatedToLogin = false
        hasNavigatedToCheckOut = false
        observeRiwayat()
        initAdapter()
        setOnClick()
    }

    private fun setOnClick(){
        riwayatAdapter.onItemClick = {
            hasNavigatedToCheckOut = true
            riwayatVM.setTransactionId(it.id)
            findNavController().navigate(R.id.action_fragmentRiwayat2_to_fragmentCheckout)
        }
    }

    private fun initAdapter(){
        binding.apply {
            rvRiwayatAfterData.adapter = riwayatAdapter
            rvRiwayatAfterData.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeRiwayat(){
        riwayatVM.getRiwayat()
        riwayatVM.riwayat.observe(viewLifecycleOwner){
            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val data = it.data
                    if(data?.data!!.isEmpty()){
                        binding.apply {
                            imgEmpty.visibility = View.VISIBLE
                            imgTxtEmpty.visibility = View.VISIBLE
                            rvRiwayatAfterData.visibility = View.GONE
                        }
                    }else{
                        binding.apply {
                            imgEmpty.visibility = View.GONE
                            imgTxtEmpty.visibility = View.GONE
                            rvRiwayatAfterData.visibility = View.VISIBLE
                            for(item in data.data){
                                riwayatAdapter.setFilteredRiwayat(data.data, item.userId)
                            }
                        }
                    }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun observeIsLoggedIn(){
        riwayatVM.getToken().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.apply {
                    linearLayoutAfterData.visibility = View.VISIBLE
                    layoutNonLogin.visibility = View.GONE
                }
            } else {
                binding.apply {
                    linearLayoutAfterData.visibility = View.GONE
                    layoutNonLogin.visibility = View.VISIBLE
                    btnMasukRiwayat.setOnClickListener {
                        navigateToLoginFragment()
                    }
                }
            }
        }
    }

    private fun navigateToLoginFragment() {
        hasNavigatedToLogin = true
        findNavController().navigate(R.id.loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (!hasNavigatedToLogin) {
            isRiwayat = false
        }

        if (!hasNavigatedToCheckOut) {
            isRiwayatCheckout = false
        }
    }
}