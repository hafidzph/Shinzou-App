package com.geminiboy.finalprojectbinar.ui.searchresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSearchResultBinding
import com.geminiboy.finalprojectbinar.model.date.DateDeparture
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.ui.searchresult.adapter.DateDepartureAdapter
import com.geminiboy.finalprojectbinar.ui.searchresult.adapter.SearchFlightAdapter
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val searchVM: SearchResultViewModel by viewModels()
    private val dateAdapter = DateDepartureAdapter()
    private val flightAdapter = SearchFlightAdapter()
    private val dateList = mutableListOf<DateDeparture>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

        observeDate()
        setTextAppBar()
        setBinding()
        observeTicket()
        getAnotherResponseTicket()
    }
    private fun setBinding(){
        binding.apply {
            rvDateBar.adapter = dateAdapter
            rvDateBar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_searchResultFragment_to_homeFragment)
            }
        }
    }

    private fun getAnotherResponseTicket(){
        dateAdapter.onItemClick = {
            searchVM.getSearch(formatDate2(it.dateDeparture))
        }
    }

    private fun observeTicket() {
        searchVM.getDate().observe(viewLifecycleOwner){
            searchVM.getSearch(formatDate(it.substringAfter(", ")))
        }
        searchVM.search.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.apply {
                        animationView.visibility = View.VISIBLE
                        rvHasilPencarian.visibility = View.GONE
                        containerEmpty.visibility = View.GONE
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        animationView.visibility = View.GONE
                        if (it.data!!.data.isEmpty()) {
                            containerEmpty.visibility = View.VISIBLE
                        } else {
                            flightAdapter.submitData(it.data.data)
                            rvHasilPencarian.visibility = View.VISIBLE
                            rvHasilPencarian.adapter = flightAdapter
                            rvHasilPencarian.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        animationView.visibility = View.VISIBLE
                        rvHasilPencarian.visibility = View.GONE
                        containerEmpty.visibility = View.GONE
                    }
                    Toast(requireContext()).showCustomToast(
                        it.message!!,
                        requireActivity(),
                        R.layout.toast_alert_red)
                    Log.d("Error Search", it.message)
                }
            }
        }
    }

    private fun observeDate(){
        searchVM.getDate().observe(viewLifecycleOwner) {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(it.substringAfter(", ")) as Date

            for (i in 0 until 7) {
                val date = calendar.time
                val dayName = SimpleDateFormat("EEEE", Locale("id", "ID")).format(date)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID")).format(date)
                dateList.add(DateDeparture(dayName, formattedDate))
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            dateAdapter.submitData(dateList as ArrayList<DateDeparture>)
        }
    }

    private fun setTextAppBar(){
        binding.apply {
            searchVM.getDestinationCode().observe(viewLifecycleOwner) {
                tvDeparture.text = it
            }

            searchVM.getPassengerJoinSeat().observe(viewLifecycleOwner){
                tvDetail.text = it
            }
        }
    }

    private fun formatDate2(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(dateString) as Date
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        return outputFormat.format(date)
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(dateString) as Date
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        return outputFormat.format(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}