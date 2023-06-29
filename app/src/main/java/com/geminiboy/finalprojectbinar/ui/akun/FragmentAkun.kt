package com.geminiboy.finalprojectbinar.ui.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentAkunBinding
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.ui.bottomsheet.login.LoginRequiredSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAkun : Fragment() {
    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    private val akunVM: AkunViewModel by viewModels()
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
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}