package com.geminiboy.finalprojectbinar.ui.searchresult.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geminiboy.finalprojectbinar.databinding.ItemResultTicketBinding
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightOneTrip.Data
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
                tvKeberangkatan.text = formatTime(item.departureTime)
                tvTiba.text = formatTime(item.arrivalTime)
                tvInitial.text = item.originAirport.locationAcronym
                tvInitialDua.text = item.destinationAirport.locationAcronym
                tvHarga.text = formatCurrency(item.price)
                tvJenisPesawat.text = "${item.airline.airlineName} - ${item.classX}"
                Glide.with(itemView).load(item.airline.airlineImage).into(binding.ivLogoPesawat)
                tvEstimasi.text = formatDuration(formatTime(item.departureTime), formatTime(item.arrivalTime))
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

    @SuppressLint("SimpleDateFormat")
    fun formatTime(time: String): String{
        val departureTime = time
        val inputFormat = SimpleDateFormat("HH:mm:ss.SSSS")
        val outputFormat = SimpleDateFormat("HH:mm")
        return inputFormat.parse(departureTime)?.let { outputFormat.format(it) }!!
    }

    fun formatCurrency(amount: Int): String {
        val decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = '.'
        }
        val decimalFormat = DecimalFormat("#,##0", decimalFormatSymbols)

        return "IDR " + decimalFormat.format(amount)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDuration(departureTime: String, arrivalTime: String): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
        val departure = Calendar.getInstance()
        val arrival = Calendar.getInstance()

        departure.time = timeFormat.parse(departureTime)!!
        arrival.time = timeFormat.parse(arrivalTime)!!

        if (arrival.before(departure)) {
            arrival.add(Calendar.DAY_OF_MONTH, 1)
        }

        val durationInMillis = arrival.timeInMillis - departure.timeInMillis
        val durationInMinutes = durationInMillis / (1000 * 60)
        val durationInHours = durationInMinutes / 60
        val remainingMinutes = durationInMinutes % 60

        return "${durationInHours}h ${remainingMinutes}m"
    }

}