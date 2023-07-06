package com.geminiboy.finalprojectbinar.ui.notification.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemNotifikasiBinding
import com.geminiboy.finalprojectbinar.model.notification.NotificationResponse.Data
import java.text.SimpleDateFormat
import java.util.Locale

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private var diffCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Data>) = differ.submitList(value)

    inner class ViewHolder(private val binding: ItemNotifikasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            binding.apply {
                DetailPromo.text = item.message
                tanggal.text = convertDateTime(item.createdAt)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(
        ItemNotifikasiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(differ.currentList[position])

    fun convertDateTime(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM, HH:mm", Locale("id", "ID"))
        val date = inputFormat.parse(dateTime)
        return outputFormat.format(date!!)
    }
}