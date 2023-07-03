package com.geminiboy.finalprojectbinar.ui.checkout.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemCountpassengersBinding
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse.Data.Ticket

class ItemCountPassengerAdapter(): RecyclerView.Adapter<ItemCountPassengerAdapter.ItemViewHolder>(){
    private var diffCallback = object : DiffUtil.ItemCallback<Ticket>(){
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Ticket>) = differ.submitList(value)

    class ItemViewHolder(private val binding: ItemCountpassengersBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item : Ticket, position: Int){
            binding.tvPenumpang.text = "Penumpang ${position + 1}"
            binding.setPenumpang.text = "${item.passenger.title} ${item.passenger.name}"
            binding.setIdPenumpang.text = "ID: ${item.id}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCountpassengersBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ItemCountPassengerAdapter.ItemViewHolder, position: Int) = holder.bind(differ.currentList[position], position)
}