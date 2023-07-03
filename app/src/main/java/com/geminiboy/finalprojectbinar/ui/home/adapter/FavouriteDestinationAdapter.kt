package com.geminiboy.finalprojectbinar.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.DestinationItemBinding
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse.Data
import com.geminiboy.finalprojectbinar.utils.Utils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class FavouriteDestinationAdapter: RecyclerView.Adapter<FavouriteDestinationAdapter.ViewHolder>() {
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

    inner class ViewHolder(private val binding: DestinationItemBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: Data){
            binding.apply {
                tvRoute.text = "${item.originAirport.location} -> ${item.destinationAirport.location}"
                tvPlane.text = item.airline.airlineName
                tvDate.text = formatTimeRange(item.departureDate, item.arrivalDate)
                tvPrice.text = Utils().formatCurrency(item.price)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(DestinationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(differ.currentList[position])

    private fun formatTimeRange(departureDate: String, arrivalDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))

        return try {
            val startDate = inputFormat.parse(departureDate)!!
            val endDate = inputFormat.parse(arrivalDate)!!

            val startDay = SimpleDateFormat("dd", Locale("id", "ID")).format(startDate)
            val endDay = SimpleDateFormat("dd", Locale("id", "ID")).format(endDate)

            val month = SimpleDateFormat("MMMM", Locale("id", "ID")).format(startDate)
            val year = SimpleDateFormat("yyyy", Locale("id", "ID")).format(startDate)

            "$startDay - $endDay $month $year"
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

}