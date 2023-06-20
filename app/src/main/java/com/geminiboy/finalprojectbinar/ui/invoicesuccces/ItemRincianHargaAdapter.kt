package com.geminiboy.finalprojectbinar.ui.invoicesuccces

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemRincianhargaBinding

class ItemRincianHargaAdapter(private val itemList:List<ItemRincianHarga>):RecyclerView.Adapter<ItemRincianHargaAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val binding:ItemRincianhargaBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemRincianHarga){
            binding.setTypePassenger.text = item.tipePassenger
            binding.setHargaTypePassenger.text = item.harga
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRincianhargaBinding.inflate(inflater,parent,false)
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