package alarmnews

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.TimePicker

import java.util.Calendar

class CalendarCall(private val activity: Activity) {
    private var calendar: Calendar? = null
    private var calset: Calendar? = null
    private var dialog: AlarmDialog? = null
    private var alarmManager: AlarmManager? = null
    internal var hour: String
    private val onTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
        val calnow = Calendar.getInstance()
        calset = calnow.clone() as Calendar
        calset!!.set(Calendar.HOUR_OF_DAY, i)
        calset!!.set(Calendar.MINUTE, i1)
        calset!!.set(Calendar.SECOND, 0)
        calset!!.set(Calendar.MILLISECOND, 0)
        if (calset!!.compareTo(calnow) <= 0)
            calset!!.add(Calendar.DATE, 1)
        hour = calset!!.time.toString()
        dialog!!.setTxt(hour)
    }

    fun setCalendar(dialog: AlarmDialog) {
        calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(activity, onTimeSetListener, calendar!!.get(Calendar.HOUR_OF_DAY), calendar!!.get(Calendar.MINUTE), true)
        timePickerDialog.setTitle("Alarm Ayarı")
        timePickerDialog.show()
        this.dialog = dialog
    }

    fun startAlarm(calendar: Calendar?, id: Int) {
        val intent = Intent(activity.baseContext, MainAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity.baseContext, id, intent, 0)
        alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar!!.timeInMillis, (24 * 60 * 60 * 1000).toLong(), pendingIntent)
    }

    fun Start(id: Int) {
        startAlarm(calset, id)
    }

    fun setAlarm(alarm: Alarm) {
        val calnow = Calendar.getInstance()
        calset = calnow.clone() as Calendar
        calset!!.set(Calendar.HOUR_OF_DAY, alarm.hour)
        calset!!.set(Calendar.MINUTE, alarm.minute)
        calset!!.set(Calendar.SECOND, 0)
        calset!!.set(Calendar.MILLISECOND, 0)
        if (calset!!.compareTo(calnow) <= 0)
            calset!!.add(Calendar.DATE, 1)
        startAlarm(calset, alarm.id)
    }
}