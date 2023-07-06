package com.geminiboy.finalprojectbinar.ui.notification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentNotifikasiBinding
import com.geminiboy.finalprojectbinar.ui.notification.adapter.NotificationAdapter
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentNotifikasi : Fragment() {
    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!
    private val notifikasiVM: NotificationViewModel by viewModels()
    private val notifikasiAdapter: NotificationAdapter = NotificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUpdate()
        init()
    }

    private fun init(){
        binding.apply {
            rvNotifikasi.adapter = notifikasiAdapter
            rvNotifikasi.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeUpdate(){
        notifikasiVM.notification()
        notifikasiVM.notification.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    notifikasiAdapter.submitData(it.data!!.data)
                }
                is Resource.Error -> {
                    Toast(requireContext()).showCustomToast(
                        it.message!!,
                        requireActivity(),
                        R.layout.toast_alert_red
                    )
                    Log.d("ERROR NOTIF", it.message)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}