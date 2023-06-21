package com.geminiboy.finalprojectbinar.ui.bottomsheet.searchingdestination

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geminiboy.finalprojectbinar.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchingDestinationSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searching_destination_sheet, container, false)
    }
}