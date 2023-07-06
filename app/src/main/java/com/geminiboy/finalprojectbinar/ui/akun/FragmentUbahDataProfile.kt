package com.geminiboy.finalprojectbinar.ui.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentUbahDataProfileBinding
import com.geminiboy.finalprojectbinar.model.user.UpdateProfileBody
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentUbahDataProfile : Fragment() {
    private var _binding: FragmentUbahDataProfileBinding? = null
    private val binding get() = _binding!!
    private val updateVM: UbahProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUbahDataProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
        init()
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSimpan.setOnClickListener {
                updateVM.getId().observe(viewLifecycleOwner){
                    updateVM.updateProfile(it, UpdateProfileBody(etEmail.text.toString(),
                        etNamaLengkapPenumpang.text.toString(),
                        etNoTelp.text.toString()))
                }
            }
        }
        observeUpdate()
    }

    private fun init(){
        binding.apply {
            updateVM.apply {
                getEmail().observe(viewLifecycleOwner){
                    etEmail.setText(it)
                }

                getName().observe(viewLifecycleOwner){
                    etNamaLengkapPenumpang.setText(it)
                }

                getPhone().observe(viewLifecycleOwner){
                    etNoTelp.setText(it)
                }
            }
        }
    }

    private fun observeUpdate(){
        updateVM.update.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast(requireContext()).showCustomToast(
                        "Data pengguna telah berhasil diperbarui.",
                        requireActivity(),
                        R.layout.toast_alert_green
                    )
                    updateVM.setNameUser(it.data?.data!!.name)
                    updateVM.setPhoneUser(it.data.data.phoneNumber)
                    updateVM.setEmailUser(it.data.data.email)
                    findNavController().navigate(R.id.action_fragmentUbahDataProfile_to_fragmentAkun2)
                }
                is Resource.Error -> {
                    Toast(requireContext()).showCustomToast(
                        it.message!!,
                        requireActivity(),
                        R.layout.toast_alert_red
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}