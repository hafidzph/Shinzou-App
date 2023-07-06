package com.geminiboy.finalprojectbinar.ui.riwayat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemRiwayatBinding
import com.geminiboy.finalprojectbinar.model.date.DateDeparture
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse
import com.geminiboy.finalprojectbinar.model.historytransaction.TransactionResponse.Data
import com.geminiboy.finalprojectbinar.utils.Utils

class RiwayatAdapter: RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {
    private var diffCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    private var idUser: String = ""

    fun setFilteredRiwayat(riwayat: List<Data>, idUser: String) {
        this.idUser = idUser
        val filteredList = riwayat.filter { it.userId.equals(idUser, ignoreCase = true) }
        differ.submitList(filteredList)
    }

    var onItemClick: ((Data) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemRiwayatBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: Data){
            binding.apply {
                tvDeparture.text = item.departureFlight.originAirport.location
                tvTanggal.text = Utils().formatDate3(item.departureFlight.departureDate)
                tvJam.text = Utils().formatTime(item.departureFlight.departureTime)
                tvEstimasi.text = Utils().formatDuration(
                    Utils().formatTime(item.departureFlight.departureTime),
                    Utils().formatTime(item.departureFlight.arrivalTime)
                )
                tvDepartureDua.text = item.departureFlight.destinationAirport.location
                tvTanggalDua.text = Utils().formatDate3(item.departureFlight.arrivalDate)
                tvJamDua.text = Utils().formatTime(item.departureFlight.arrivalTime)
                noBookingCode.text = item.bookingCode
                typeClass.text = item.departureFlight.classX
                tvHarga.text = "IDR ${Utils().formatCurrency(item.ammount)}"
                if(item.returnFlight != null){
                    containerReturnFlight.visibility = View.VISIBLE
                    ivLocationDuaReturn.visibility = View.VISIBLE
                    containerEstimasiReturn.visibility = View.VISIBLE
                    containerReturnFlight1.visibility = View.VISIBLE
                    ivLocationReturn.visibility = View.VISIBLE
                    tvReturnFlight.text = item.returnFlight.originAirport.location
                    tvReturnTgl.text = Utils().formatDate3(item.returnFlight.departureDate)
                    tvReturnJam.text = Utils().formatTime(item.returnFlight.departureTime)
                    tvEstimasiReturn.text = Utils().formatDuration(
                        Utils().formatTime(item.returnFlight.departureTime),
                        Utils().formatTime(item.returnFlight.arrivalTime)
                    )
                    tvDepartureDuaReturn.text = item.returnFlight.destinationAirport.location
                    tvTanggalDuaReturn.text = Utils().formatDate3(item.returnFlight.arrivalDate)
                    tvJamDuaReturn.text = Utils().formatTime(item.returnFlight.arrivalTime)
                }
                cvItemRiwayat.setOnClickListener{
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(differ.currentList[position])

}