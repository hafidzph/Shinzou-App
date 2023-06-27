package com.geminiboy.finalprojectbinar.ui.bottomsheet.searchingdestination

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSearchingDestinationSheetBinding
import com.geminiboy.finalprojectbinar.ui.bottomsheet.searchingdestination.adapter.SearchingAdapter
import com.geminiboy.finalprojectbinar.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchingDestinationSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSearchingDestinationSheetBinding? = null
    private val binding get() = _binding!!
    private val searchDestinationVM: SearchingDestinationViewModel by viewModels()
    private val searchingAdapter = SearchingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchingDestinationSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDataAirport()
        setupRecyclerViews()
        search()
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Tindakan yang akan dilakukan saat pengguna menekan tombol "Submit" pada keyboard
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Tindakan yang akan dilakukan saat teks pencarian berubah
                newText?.let { query ->
                    searchingAdapter.filterData(query)
                }
                return true
            }
        })
    }

    private fun setupRecyclerViews() {
        binding.rvAsalTujuan.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchingAdapter
        }
    }

    private fun fetchDataAirport() {
        searchDestinationVM.getToken().observe(viewLifecycleOwner) { token ->
            searchDestinationVM.getAirport(token)
        }
        searchDestinationVM.airport.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Tindakan yang akan dilakukan saat sedang memuat data
                }
                is Resource.Success -> {
                    searchingAdapter.submitData(resource.data?.data!!)
                }
                is Resource.Error -> {
                    Log.d("Error Fetching data Airport", resource.message ?: "Unknown Error")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}