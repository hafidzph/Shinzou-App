package com.geminiboy.finalprojectbinar.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentLoginBinding
import com.geminiboy.finalprojectbinar.databinding.FragmentRegisterBinding
import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.ui.MainActivity
import com.geminiboy.finalprojectbinar.utils.showCustomToast
import com.geminiboy.finalprojectbinar.wrapper.Resource
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginVM: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

        binding.apply {
            btnLogin.setOnClickListener {
                if(isValid()){
                    val email = binding.etEmail.text.toString().trim()
                    val password = binding.masukanPassword.text.toString().trim()
                    loginVM.login(LoginBody(email, password))
                }
            }

            tvDaftarDisini.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            tvLupaPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
        }
        observe()
    }

    private fun isValid(): Boolean {
        var isValid = true

        val email = binding.etEmail.text.toString().trim()
        val password = binding.masukanPassword.text.toString().trim()

        if (email.isEmpty() && password.isEmpty()) {
            Toast(requireContext()).showCustomToast(
                "Email dan Password tidak valid!",
                requireActivity(),
                R.layout.toast_alert_red
            )
            binding.etEmail.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_error_state)
            binding.masukanPassword.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_error_state)
            isValid = false
        }else if(email.isEmpty()){
            Toast(requireContext()).showCustomToast(
                "Email tidak valid!",
                requireActivity(),
                R.layout.toast_alert_red
            )
            binding.etEmail.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_error_state)
            isValid = false
        }else if(password.isEmpty()){
            Toast(requireContext()).showCustomToast(
                "Password tidak valid!",
                requireActivity(),
                R.layout.toast_alert_red
            )
            binding.masukanPassword.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_error_state)
            isValid = false
        }

        return isValid
    }

    private fun observe(){
        loginVM.login.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast(requireContext()).showCustomToast(
                        "Login berhasil",
                        requireActivity(),
                        R.layout.toast_alert_green
                    )
                    loginVM.setToken(resource.data!!.data.token)
                    lifecycleScope.launch { delay(500) }
                    findNavController().navigate(R.id.homeFragment)
                    findNavController().popBackStack(R.id.loginFragment, true)
                }
                is Resource.Error -> {
                    Toast(requireContext()).showCustomToast(
                        "Email atau kata sandi salah",
                        requireActivity(),
                        R.layout.toast_alert_red
                    )
                }
            }
        }
    }

//    private fun navigateToDestinationAfterLogin() {
//        val currentDestination = findNavController().currentDestination?.id
//
//        val destinationId = determineDestinationId(currentDestination)
//        if (destinationId != null) {
//            findNavController().popBackStack(destinationId, false)
//        } else {
//            findNavController().popBackStack()
//        }
//    }
//
//
//    private fun determineDestinationId(currentDestination: Int?): Int? {
//        if (currentDestination == R.id.fragmentAkun2) {
//            return R.id.fragmentAkun2
//        }
//        if (currentDestination == R.id.fragmentDetailPenerbangan) {
//            return R.id.fragmentDetailPenerbangan
//        }
//        if (currentDestination == R.id.fragmentRiwayat2) {
//            return R.id.fragmentRiwayat2
//        }
//        return null
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}