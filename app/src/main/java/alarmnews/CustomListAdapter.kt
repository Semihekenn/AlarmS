package alarmnews

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

import com.semiheken.alarms.R

class CustomListAdapter(private val alarms: List<Alarm>, private val activity: Activity) : BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var alarm: Alarm? = null
    private var txtid: TextView? = null

    init {
        layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return alarms.size
    }

    override fun getItem(i: Int): Any {
        return alarms[i]
    }

    override fun getItemId(i: Int): Long {
        return alarms[i].id.toLong()
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val viewRow: View
        alarm = alarms[i]
        val txtHour: TextView
        val txtMessage: TextView
        val layout: LinearLayout
        viewRow = layoutInflater.inflate(R.layout.row, null)
        txtid = viewRow.findViewById(R.id.txtid)
        txtHour = viewRow.findViewById(R.id.txthour)
        txtMessage = viewRow.findViewById(R.id.txtMessage)
        layout = viewRow.findViewById(R.id.lytRow)
        txtid!!.text = alarm!!.id.toString() + ""
        txtHour.text = alarm!!.hour.toString() + ":" + alarm!!.minute
        txtMessage.text = alarm!!.msg
        viewRow.setOnLongClickListener {
            txtid = viewRow.findViewById(R.id.txtid)
            val adapter = ListAdapter(activity)
            adapter.setAlarm(adapter.getAlarm(Integer.parseInt(txtid!!.text.toString())))
            true
        }

        if (alarm!!.visible == 0)
            layout.setBackgroundResource(R.color.black_light)
        viewRow.setOnClickListener {
            txtid = viewRow.findViewById(R.id.txtid)
            val adapter = ListAdapter(activity)
            alarm = adapter.getAlarm(Integer.parseInt(txtid!!.text.toString()))
            if (alarm!!.visible == 1) {
                layout.setBackgroundResource(R.color.black_light)
                alarm!!.visible = 0
                var alarmDialog

                adapter.updateAlarm(alarm)
                adapter.cancelAlarm(alarm!!.id)
            } else if (alarm!!.visible == 0) {
                layout.setBackgroundResource(R.color.black)
                alarm!!.visible = 1
                adapter.updateAlarm(alarm)
                adapter.openAlarm(alarm)
            }
        }
        return viewRow
    }
}