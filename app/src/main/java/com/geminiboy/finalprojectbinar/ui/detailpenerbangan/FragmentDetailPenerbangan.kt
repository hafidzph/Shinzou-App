package com.geminiboy.finalprojectbinar.ui.detailpenerbangan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geminiboy.finalprojectbinar.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDetailPenerbangan : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_penerbangan, container, false)
    }
}