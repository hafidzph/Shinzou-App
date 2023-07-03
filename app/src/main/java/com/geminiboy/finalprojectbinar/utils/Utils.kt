package com.geminiboy.finalprojectbinar.utils
import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.geminiboy.finalprojectbinar.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utils{
    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(dateString) as Date
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        return outputFormat.format(date)
    }

    fun formatDate2(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(dateString) as Date
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        return outputFormat.format(date)
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

        return decimalFormat.format(amount)
    }

    fun formatDate3(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        val date = inputFormat.parse(dateString) as Date
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return outputFormat.format(date)
    }
}
fun Toast.showCustomToast(message: String, activity: Activity, layoutResId: Int) {
    val layout = activity.layoutInflater.inflate(
        layoutResId,
        activity.findViewById(R.id.toast_container)
    )

    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message
    textView.setLines(2)
    textView.gravity = Gravity.CENTER

    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}
