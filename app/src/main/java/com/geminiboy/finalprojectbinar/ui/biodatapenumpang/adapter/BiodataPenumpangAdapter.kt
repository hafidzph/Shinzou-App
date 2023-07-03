package com.geminiboy.finalprojectbinar.ui.biodatapenumpang.adapter

import android.R
import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geminiboy.finalprojectbinar.databinding.ItemFormBiodataPenumpangBinding
import com.geminiboy.finalprojectbinar.model.flight.TransactionBody

class BiodataPenumpangAdapter : RecyclerView.Adapter<BiodataPenumpangAdapter.ViewHolder>() {
    private var babyPassengerCount = 0
    private var childrenPassengerCount = 0
    private var adultPassengerCount = 0
    private var title = arrayOf("Mr.", "Mrs.")
    private val passengerList = mutableListOf<TransactionBody.Passenger>()

    private var diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(babyCount: Int, childrenCount: Int, adultCount: Int) {
        babyPassengerCount = babyCount
        childrenPassengerCount = childrenCount
        adultPassengerCount = adultCount

        val totalPassengerCount = babyPassengerCount + childrenPassengerCount + adultPassengerCount

        passengerList.clear()
        for (i in 0 until totalPassengerCount) {
            passengerList.add(TransactionBody.Passenger("", "", "", "", "", ""))
        }

        differ.submitList(List(totalPassengerCount) { it })

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemFormBiodataPenumpangBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val titleAdapter = ArrayAdapter(binding.root.context, R.layout.simple_spinner_item, title)
            titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val passengerType = when {
                position < adultPassengerCount -> "Adult"
                position < (adultPassengerCount + childrenPassengerCount) -> "Children"
                else -> "Baby"
            }
            val passengerNumber =
                position % (babyPassengerCount + childrenPassengerCount + adultPassengerCount) + 1

            binding.apply {
                tvPassengers.text = "Passenger $passengerNumber - $passengerType"
                titlePassengerSpinner.adapter = titleAdapter

                val currentPassenger = passengerList[position]

                titlePassengerSpinner.setSelection(title.indexOf(currentPassenger.title))
                etNameClan.setText(currentPassenger.family_name)
                etNamaLengkapPenumpang.setText(currentPassenger.name)
                etIdentity.setText(currentPassenger.identity_number)
                etNomorTelepon.setText(currentPassenger.phone_number)

                switchToggle.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked){
                        layoutNameClan.visibility = View.VISIBLE
                    }else{
                       layoutNameClan.visibility = View.GONE
                    }
                }

                titlePassengerSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selectedTitle = title[position]
                            currentPassenger.title = selectedTitle
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }

                etNameClan.addTextChangedListener(TextChangeListener { text ->
                    currentPassenger.family_name = text
                })

                etNamaLengkapPenumpang.addTextChangedListener(TextChangeListener { text ->
                    currentPassenger.name = text
                })

                etIdentity.addTextChangedListener(TextChangeListener { text ->
                    currentPassenger.identity_number = text
                })

                etNomorTelepon.addTextChangedListener(TextChangeListener { text ->
                    currentPassenger.phone_number = text
                })

                etNomorKursi.addTextChangedListener(TextChangeListener { text ->
                    currentPassenger.seat_number = text
                })

            }
        }
    }

    inner class TextChangeListener(private val listener: (String) -> Unit) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    fun getPassengers(): List<TransactionBody.Passenger> {
        return passengerList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFormBiodataPenumpangBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}
