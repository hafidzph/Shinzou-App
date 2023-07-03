package com.geminiboy.finalprojectbinar.ui.biodatapemesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentBiodataPemesanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBiodataPemesan : Fragment() {
    private var _binding: FragmentBiodataPemesanBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBiodataPemesanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {
            switchToggle.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    NamaKeluarga.visibility = View.VISIBLE
                    tvNamaKeluarga.visibility = View.VISIBLE
                }else{
                    NamaKeluarga.visibility = View.GONE
                    tvNamaKeluarga.visibility = View.GONE
                }
            }

            btnSimpanBiodata.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentBiodataPemesan_to_fragmentBiodataPenumpang)
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}