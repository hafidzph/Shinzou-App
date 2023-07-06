package com.geminiboy.finalprojectbinar.ui.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentRiwayatBinding
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.ui.detailpenerbangan.FragmentDetailPenerbangan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRiwayat : Fragment() {
    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private val riwayatVM: RiwayatViewModel by viewModels()

    companion object{
        var isRiwayat = false
        var hasNavigatedToLogin = false
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
        hasNavigatedToLogin = false
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
    }
}