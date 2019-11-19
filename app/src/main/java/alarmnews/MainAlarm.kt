package alarmnews

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

class MainAlarm : BroadcastReceiver() {
    private val activity: Activity? = null
    override fun onReceive(context: Context, intent: Intent) {

        val intent2 =  Intent(context, MainActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent2)
        val toastWhite: ToastWhite
        toastWhite = ToastWhite(context)
        toastWhite.setText("ALARM İS CALL")
        toastWhite.Show()
        var Musicalarm: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (Musicalarm == null)
            Musicalarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ringtone = RingtoneManager.getRingtone(context, Musicalarm)
        ringtone!!.play()
        Control = true
    }

    companion object {
        private var ringtone: Ringtone? = null
        var isopen = false
        var Control: Boolean = false
        fun Stop() {
            ringtone!!.stop()
        }
    }
}
