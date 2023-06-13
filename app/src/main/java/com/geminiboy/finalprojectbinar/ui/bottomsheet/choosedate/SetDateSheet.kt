package com.geminiboy.finalprojectbinar.ui.bottomsheet.choosedate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentSetDateBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
@AndroidEntryPoint
class SetDateSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSetDateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var startDateString = ""
        var endDateString = ""
        binding.apply {
            btnCloseTanggal.setOnClickListener {
                dismiss()
            }

            calendar.setCalendarListener(object : CalendarListener {
                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
                    startDateString = dateFormat.format(startDate.time)
                    endDateString = dateFormat.format(endDate.time)
                }

                override fun onFirstDateSelected(startDate: Calendar) {
                }
            })

            btnPilihTanggal.setOnClickListener {
                tanggalBerangkat.text = startDateString
                tanggalPulang.text = endDateString
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}