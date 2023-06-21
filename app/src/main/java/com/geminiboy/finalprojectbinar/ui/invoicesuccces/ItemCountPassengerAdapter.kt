package com.geminiboy.finalprojectbinar.ui.invoicesuccces

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemCountpassengersBinding


class ItemCountPassengerAdapter(private val itemList:List<ItemCountPassengers>):RecyclerView.Adapter<ItemCountPassengerAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val binding:ItemCountpassengersBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(item : ItemCountPassengers){
            binding.tvPenumpang.text = item.penumpang
            binding.setPenumpang.text = item.namePenumpang
            binding.setIdPenumpang.text = item.idPenumpang
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCountpassengersBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

}
