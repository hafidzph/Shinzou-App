package com.geminiboy.finalprojectbinar.ui.invoicesuccces

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.FragmentInvoiceSuccessBinding

class InvoiceSuccessFragment : Fragment() {

    lateinit var binding : FragmentInvoiceSuccessBinding
    lateinit var itemCountPassengerAdapter: ItemCountPassengerAdapter
    lateinit var itemRincianHargaAdapter: ItemRincianHargaAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvoiceSuccessBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.rvPassenger.layoutManager = layoutManager

        val itemCountPassengers = listOf(
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789"),
            ItemCountPassengers("Penumpang 1:","Habib Apis","123456789")
        )
        itemCountPassengerAdapter = ItemCountPassengerAdapter(itemCountPassengers)
        binding.rvPassenger.adapter = itemCountPassengerAdapter

        val layoutManager2 = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.rvRincianHarga.layoutManager = layoutManager2
        val itemRincianHarga = listOf(
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000"),
            ItemRincianHarga("2 Adults","IDR 9.550.000")
        )
        itemRincianHargaAdapter = ItemRincianHargaAdapter(itemRincianHarga)
        binding.rvRincianHarga.adapter = itemRincianHargaAdapter
    }
}