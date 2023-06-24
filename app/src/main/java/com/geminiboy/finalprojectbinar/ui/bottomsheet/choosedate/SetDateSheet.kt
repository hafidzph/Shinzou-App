package com.geminiboy.finalprojectbinar.ui.bottomsheet.choosedate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSetDateBinding
import com.geminiboy.finalprojectbinar.ui.home.HomeFragment
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
@AndroidEntryPoint
class SetDateSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSetDateBinding? = null
    private val binding get() = _binding!!
    private val setDateVM: SetDateViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
    private var startDateString = ""
    private var endDateString = ""
    private val currentDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnCloseTanggal.setOnClickListener {
                dismiss()
            }

            if (HomeFragment.isRoundTrip) {
                calendarRange.visibility = View.VISIBLE
                calendarSingle.visibility = View.GONE
            } else {
                calendarRange.visibility = View.GONE
                calendarSingle.visibility = View.VISIBLE
            }

            btnPilihTanggal.setOnClickListener {
                setDateVM.apply {
                    setDepartureDate(startDateString)
                    if(endDateString != "-") setReturnDate(endDateString) else setReturnDate("-")
                    lifecycleScope.launch { delay(500) }
                }
                dismiss()
            }
        }
        setDateRange()
        setDateSingle()
    }

    private fun setDateSingle(){
        binding.apply {
            calendarSingle.setCalendarListener(object : CalendarListener {
                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    if(startDate.before(currentDate)) {
                        Toast(requireContext()).showCustomToast(
                            "Tanggal sudah kadaluarsa!!",
                            requireActivity(),
                            R.layout.toast_alert_red)
                    }else {
                        startDateString = dateFormat.format(startDate.time)
                        tanggalBerangkat.text = startDateString
                        tanggalPulang.text = "-"
                    }
                }
                override fun onFirstDateSelected(startDate: Calendar) {}
            })
        }
    }

    private fun setDateRange(){
        binding.apply {
            calendarRange.setCalendarListener(object : CalendarListener {
                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    if(startDate.before(currentDate)) {
                        Toast(requireContext()).showCustomToast(
                            "Tanggal sudah kadaluarsa!!",
                            requireActivity(),
                            R.layout.toast_alert_red)
                    }else{
                        startDateString = dateFormat.format(startDate.time)
                        endDateString = dateFormat.format(endDate.time)
                        tanggalBerangkat.text = startDateString
                        tanggalPulang.text = endDateString
                    }
                }

                override fun onFirstDateSelected(startDate: Calendar) {}
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}