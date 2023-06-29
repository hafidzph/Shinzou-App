package com.geminiboy.finalprojectbinar.ui.searchresult.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.ItemResultTicketBinding
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightOneTrip.Data
import com.geminiboy.finalprojectbinar.utils.Utils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchFlightAdapter : RecyclerView.Adapter<SearchFlightAdapter.ViewHolder>() {
    private var diffCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Data>) = differ.submitList(value)

    inner class ViewHolder(private val binding: ItemResultTicketBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: Data,){
            binding.apply {
                tvKeberangkatan.text = Utils().formatTime(item.departureTime)
                tvTiba.text = Utils().formatTime(item.arrivalTime)
                tvInitial.text = item.originAirport.locationAcronym
                tvInitialDua.text = item.destinationAirport.locationAcronym
                tvHarga.text = Utils().formatCurrency(item.price)
                tvJenisPesawat.text = "${item.airline.airlineName} - ${item.classX}"
                Glide.with(itemView).load(item.airline.airlineImage).into(binding.ivLogoPesawat)
                tvEstimasi.text = Utils().formatDuration(Utils().formatTime(item.departureTime), Utils().formatTime(item.arrivalTime))

                containerTicket.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("id", item.id)
                    }
                    it.findNavController().navigate(R.id.action_searchResultFragment_to_fragmentDetailPenerbangan, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(ItemResultTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size
}