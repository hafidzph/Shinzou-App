package com.geminiboy.finalprojectbinar.ui.searchresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSearchResultBinding
import com.geminiboy.finalprojectbinar.model.date.DateDeparture
import com.geminiboy.finalprojectbinar.ui.searchresult.adapter.DateDepartureAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val searchVM: SearchResultViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateAdapter = DateDepartureAdapter()

        searchVM.getDate().observe(viewLifecycleOwner) {
            val dateList = mutableListOf<DateDeparture>()
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
        binding.apply {
            rvDateBar.adapter = dateAdapter
            rvDateBar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_searchResultFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}