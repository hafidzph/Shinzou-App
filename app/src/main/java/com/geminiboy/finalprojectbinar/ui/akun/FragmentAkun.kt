package com.geminiboy.finalprojectbinar.ui.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentAkunBinding
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAkun : Fragment() {
    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    private val akunVM: AkunViewModel by viewModels()

    companion object{
        var isProfile = false
        var hasNavigatedToLogin = false
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        observeIsLoggedIn()
        init()
        isProfile = true
        hasNavigatedToLogin = false
    }

    private fun init(){
        binding.apply {
            tvLogout.setOnClickListener {
                akunVM.clear()
                Toast(requireContext()).showCustomToast(
                    "Logout berhasil",
                    requireActivity(),
                    R.layout.toast_alert_green
                )
            }
        }
    }

    private fun observeIsLoggedIn(){
        akunVM.getToken().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.apply {
                    layoutUserLogged.visibility = View.VISIBLE
                    layoutNonLogin.visibility = View.GONE
                }
            } else {
                binding.apply {
                    layoutUserLogged.visibility = View.GONE
                    layoutNonLogin.visibility = View.VISIBLE
                    btnLogin.setOnClickListener {
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
            isProfile = false
        }
    }
}