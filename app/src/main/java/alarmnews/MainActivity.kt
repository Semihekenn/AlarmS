package alarmnews

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView

import com.semiheken.alarms.R

class MainActivity : AppCompatActivity() {
    private var listView: ListView? = null
    private var listAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this
        MainAlarm.isopen = true
        activity = this
        listAdapter = ListAdapter(this)
        listView = findViewById(R.id.viewList)
        listAdapter!!.Reload(listView)
        val button = findViewById<Button>(R.id.btnMenu)

        button.setOnClickListener {
            val alarmDialog = AlarmDialog(activity, AlarmDialog.DIALOG_ADD)
            alarmDialog.Show()
        }
        button.setOnLongClickListener {
            for (i in 0 until listAdapter!!.alarmList.size) {
                val alarmFlg = listAdapter!!.alarmList[i]
                if (alarmFlg.visible == 1) {

                    alarmFlg.visible = 0
                    listAdapter!!.updateAlarm(alarmFlg)
                    listAdapter!!.cancelAlarm(alarmFlg.id)
                } else if (alarmFlg.visible == 0) {

                    alarmFlg.visible = 1
                    listAdapter!!.updateAlarm(alarmFlg)
                    listAdapter!!.openAlarm(alarmFlg)
                }
            }
            listAdapter!!.Reload(listView)
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainAlarm.isopen = false
    }

    companion object {
        internal var activity: Activity
    }
}
