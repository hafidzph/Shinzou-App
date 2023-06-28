package com.geminiboy.finalprojectbinar.ui.searchresult.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.R
import com.geminiboy.finalprojectbinar.databinding.ItemListLoadingDateBinding
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.date.DateDeparture
import com.geminiboy.finalprojectbinar.ui.searchresult.SearchResultViewModel

@Suppress("DEPRECATION")
class DateDepartureAdapter: RecyclerView.Adapter<DateDepartureAdapter.DateViewHolder>() {
    private var diffCallback = object : DiffUtil.ItemCallback<DateDeparture>(){
        override fun areItemsTheSame(oldItem: DateDeparture, newItem: DateDeparture): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DateDeparture, newItem: DateDeparture): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private var selectedPosition = -1
    private val differ = AsyncListDiffer(this, diffCallback)
    var onItemClick: ((DateDeparture) -> Unit)? = null

    fun submitData(value: ArrayList<DateDeparture>) = differ.submitList(value)

    inner class DateViewHolder(private val binding: ItemListLoadingDateBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: DateDeparture, isLastItem: Boolean){
            binding.apply {
                tvHari.text = item.dayDeparture
                tvTanggal.text = item.dateDeparture

                if (isLastItem) {
                    divider.visibility = View.GONE
                } else {
                    divider.visibility = View.VISIBLE
                }

                if (position == 0 && selectedPosition == -1) {
                    selectedPosition = 0
                    containerDate.setBackgroundResource(R.drawable.shape_date_departure);
                    tvHari.setTextColor(Color.WHITE)
                    tvTanggal.setTextColor(Color.WHITE)
                }else if (position == selectedPosition) {
                    containerDate.setBackgroundResource(R.drawable.shape_date_departure)
                    tvHari.setTextColor(Color.WHITE)
                    tvTanggal.setTextColor(Color.WHITE)
                } else {
                    containerDate.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                    tvHari.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    tvTanggal.setTextColor(ContextCompat.getColor(itemView.context, R.color.NEUTRAL03))
                }

                binding.containerDate.setOnClickListener {
                    if (selectedPosition != position) {
                        val previousSelectedPosition = selectedPosition
                        selectedPosition = position
                        notifyItemChanged(previousSelectedPosition)
                        notifyItemChanged(selectedPosition)
                        onItemClick?.invoke(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateViewHolder = DateViewHolder(ItemListLoadingDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) =
        holder.bind(differ.currentList[position], position == differ.currentList.size - 1)

    override fun getItemCount(): Int = differ.currentList.size
}