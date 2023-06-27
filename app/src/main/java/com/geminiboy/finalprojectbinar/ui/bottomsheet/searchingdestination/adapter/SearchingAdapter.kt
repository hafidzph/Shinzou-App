package com.geminiboy.finalprojectbinar.ui.bottomsheet.searchingdestination.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemSearchBinding
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse.Data
import java.util.Locale

class SearchingAdapter : RecyclerView.Adapter<SearchingAdapter.ViewHolder>() {
    private val originalData: MutableList<Data> = mutableListOf()
    private val filteredData: MutableList<Data> = mutableListOf()

    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.apply {
                tvCity.text = item.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredData[position])
    }

    fun submitData(value: List<Data>) {
        originalData.clear()
        originalData.addAll(value)
        filterData("")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterData(query: String) {
        filteredData.clear()
        val trimmedQuery = query.trim().toLowerCase(Locale.ROOT)
        if (trimmedQuery.isEmpty()) {
            filteredData.addAll(originalData)
        } else {
            for (item in originalData) {
                if (item.location.toLowerCase(Locale.ROOT).contains(trimmedQuery)) {
                    filteredData.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}
