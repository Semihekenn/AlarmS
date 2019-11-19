package alarmnews

import android.app.Activity
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

import com.semiheken.alarms.R


class AlarmDialog(private val activity: Activity, command: Int) {
    private val dialog: AlertDialog.Builder
    private val inflater: LayoutInflater
    private val view: View
    private val txtHour: TextView
    private val txtMinute: TextView
    private val txtmsg: EditText
    private val call: CalendarCall
    private val adapter: ListAdapter
    private var alarm: Alarm? = null
    private var alertDialog: AlertDialog? = null


    init {
        dialog = AlertDialog.Builder(this.activity)
        dialog.setTitle("ALARM İS CALL")
        val alarmDialog = this
        adapter = ListAdapter(activity)
        inflater = activity.layoutInflater
        view = inflater.inflate(R.layout.alert_dialog, null)
        dialog.setView(view)
        call = CalendarCall(activity)
        txtHour = view.findViewById(R.id.txtAlertHour)
        txtMinute = view.findViewById(R.id.txtAlertMinute)
        txtmsg = view.findViewById(R.id.txtDlgMessage)

        if (command == this.DIALOG_ADD) {
            val dialogInterface = dialog.create()//dialog builderdan yeni nesne oluşturulur.
            view.findViewById<View>(R.id.btndlgOk).setOnClickListener {
                val alarm = Alarm(0, Integer.valueOf(txtHour.text.toString()),
                        Integer.valueOf(txtMinute.text.toString()), txtmsg.text.toString(), 1, 1)
                adapter.addAlarm(alarm)
                dialog.setTitle("ADD ALARM")
                val listView = activity.findViewById<ListView>(R.id.viewList)
                adapter.Reload(listView)
                call.Start(adapter.lastId)
                Cancel()
            }
        }
        if (command == this.DIALOG_DEL_UP) {
            val dialogInterface = dialog.create()
            view.findViewById<View>(R.id.btndlgCancel).visibility = View.VISIBLE
            val button = view.findViewById<Button>(R.id.btndlgOk)
            button.text = "UPDATE"
            dialog.setTitle("REMAKE ALARM")
            button.setOnClickListener {
                val alarm = Alarm(alarmDialog.alarm!!.id, Integer.valueOf(txtHour.text.toString()), Integer.valueOf(txtMinute.text.toString()), txtmsg.text.toString(), alarmDialog.alarm!!.sound, alarmDialog.alarm!!.visible)
                adapter.updateAlarm(alarm)
                val listView = activity.findViewById<ListView>(R.id.viewList)
                adapter.Reload(listView)
                call.Start(adapter.lastId)
                Cancel()
            }
        }
        if (command == this.DIALOG_POP) {
            dialog.setCancelable(false)
            val dialogInterface = dialog.create()
            view.findViewById<View>(R.id.btndlgCancel).visibility = View.GONE
            view.findViewById<View>(R.id.btndlgOk).visibility = View.VISIBLE
            view.findViewById<View>(R.id.lytHour).visibility = View.GONE
            view.findViewById<View>(R.id.txtDlgMessage).visibility = View.INVISIBLE
            val button = view.findViewById<Button>(R.id.btndlgOk)
            button.text = "CLOSE ALARM"
            view.findViewById<View>(R.id.btndlgOk).setOnClickListener {
                MainAlarm.Stop()
                MainAlarm.Control = false
                Cancel()
            }
        }
        view.findViewById<View>(R.id.btndlgCancel).setOnClickListener {
            adapter.deleteAlarm(alarm!!.id)
            val listView = activity.findViewById<ListView>(R.id.viewList)
            adapter.Reload(listView)
            Cancel()
        }
        view.findViewById<View>(R.id.lytHour).setOnClickListener { call.setCalendar(alarmDialog) }
    }

    fun Show() {
        alertDialog = this.getDialog()
        alertDialog!!.show()
    }

    fun setDialogText(alarm: Alarm) {
        txtmsg.setText(alarm.msg)
        txtHour.text = alarm.hour.toString() + ""
        txtMinute.text = alarm.minute.toString() + ""
        this.alarm = alarm
    }

    fun Cancel() {
        alertDialog!!.dismiss()
    }

    fun setTxt(hour: String) {
        view.findViewById<View>(R.id.btndlgOk).visibility = View.VISIBLE
        txtHour.setText(hour.substring(11, 13))
        val a = txtHour.text.toString()
        txtHour.text = Integer.valueOf(a).toString()
        txtMinute.setText(hour.substring(14, 16))
    }

    fun getDialog(): AlertDialog? {
        alertDialog = dialog.create()
        return alertDialog
    }

    fun closeDialog(): AlertDialog {
        alertDialog!!.dismiss()
        return alertDialog
    }

    companion object {
        val DIALOG_ADD = 0
        val DIALOG_DEL_UP = 1
        val DIALOG_POP = 2
    }
}
