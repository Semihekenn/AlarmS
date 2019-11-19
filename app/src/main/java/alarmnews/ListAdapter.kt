package alarmnews

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.ListView
import java.util.Calendar

class ListAdapter(private val activity: Activity) {
    private val connecter: Connecter
    val alarmList: List<Alarm>
        get() = connecter.list
    val lastId: Int
        get() = connecter.lastId
    val alarmForTime: Alarm
        get() {
            val string = Calendar.getInstance().time.toString()
            return connecter.getAlarmForTime(Integer.valueOf(string.substring(11, 13)), Integer.valueOf(string.substring(14, 16)))
        }

    init {
        connecter = Connecter(activity)
    }

    fun addAlarm(alarm: Alarm) {
        connecter.Makealarm(alarm)
    }

    fun Reload(listView: ListView) {
        listView.adapter = null
        val listAdapter = ListAdapter(activity)
        val alarms = listAdapter.alarmList
        val customListAdapter = CustomListAdapter(alarms, activity)
        listView.adapter = customListAdapter
        if (MainAlarm.Control == true) {
            val alarmDialog = AlarmDialog(activity, AlarmDialog.DIALOG_POP)
            alarmDialog.Show()
            val alarmNotification = AlarmNotification(activity)
        }
    }

    fun setAlarm(alarm: Alarm) {
        val alarmDialog = AlarmDialog(activity, AlarmDialog.DIALOG_DEL_UP)
        alarmDialog.setDialogText(alarm)
        alarmDialog.Show()
    }

    fun updateAlarm(alarm: Alarm) {
        connecter.Update(alarm)
    }

    fun deleteAlarm(id: Int) {
        val intent = Intent(activity.baseContext, MainAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity.baseContext, id, intent, 0)
        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        connecter.Delete(id)
    }

    fun getAlarm(id: Int): Alarm {
        return connecter.getAlarm(id)
    }

    fun cancelAlarm(id: Int) {
        val intent = Intent(activity.baseContext, MainAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity.baseContext, id, intent, 0)
        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    fun openAlarm(alarm: Alarm) {
        val calendarCall = CalendarCall(activity)
        calendarCall.setAlarm(alarm)
    }
}
