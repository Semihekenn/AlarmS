package alarmnews

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

class Connecter(private val activity: Activity) {
    var database: SQLiteDatabase? = null
        private set
    private val white: ToastWhite
    val list: List<Alarm>
        get() {
            val alarms = ArrayList<Alarm>()
            val cursor = database!!.rawQuery("SELECT * FROM Alarm", null)
            cursor.moveToFirst()
            var boole = true
            while (boole) {
                try {
                    val alarm = Alarm(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("hour")),
                            cursor.getInt(cursor.getColumnIndex("minute")), cursor.getString(cursor.getColumnIndex("message")),
                            cursor.getInt(cursor.getColumnIndex("sound")), cursor.getInt(cursor.getColumnIndex("visible")))
                    alarms.add(alarm)
                    cursor.moveToNext()
                } catch (e: Exception) {
                    boole = false
                }

            }
            return alarms
        }
    val lastId: Int
        get() {
            val cursor = database!!.rawQuery("SELECT id FROM Alarm", null)
            cursor.moveToLast()
            return cursor.getInt(cursor.getColumnIndex("id"))
        }

    init {
        white = ToastWhite(activity)
        database = activity.openOrCreateDatabase("Alarms", Context.MODE_PRIVATE, null)
        this.database = database
        val cmd = "CREATE TABLE IF NOT EXISTS Alarm(id integer  PRIMARY KEY AUTOINCREMENT , " + "hour integer(2), minute integer(2), message VARCHAR, sound integer(2),visible integer(1))"
        database!!.execSQL(cmd)
    }

    fun Makealarm(alarm: Alarm) {
        try {
            val cmd = "INSERT INTO Alarm(hour, minute, message, sound, visible) VALUES (" + alarm.hour + ", " +
                    "" + alarm.minute + ", '" + alarm.msg + "', " + alarm.getSound() + ", 1)"
            database!!.execSQL(cmd)
            white.setText("Alarm İs Set")
            white.Show()
        } catch (e: Exception) {
        }

    }

    fun Update(alarm: Alarm) {
        database!!.execSQL("UPDATE Alarm SET hour=" + alarm.hour + ", minute=" + alarm.minute + ", message='" + alarm.msg + "',sound=" + alarm.sound + ",visible=" + alarm.visible + " WHERE id=" + alarm.id + " ")
        white.setText("ALARM İS REMAKE")
        white.Show()
    }

    fun Delete(id: Int) {
        database!!.execSQL("DELETE FROM Alarm WHERE id=$id ")
        white.setText("ALARM İS DELETE")
        white.Show()
    }

    fun getAlarm(id: Int): Alarm {
        val cursor = database!!.rawQuery("SELECT * FROM Alarm WHERE id=$id ", null)
        cursor.moveToFirst()
        return Alarm(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("hour")),
                cursor.getInt(cursor.getColumnIndex("minute")), cursor.getString(cursor.getColumnIndex("message")),
                cursor.getInt(cursor.getColumnIndex("sound")), cursor.getInt(cursor.getColumnIndex("visible")))
    }

    fun getAlarmForTime(hour: Int, minute: Int): Alarm {
        val cursor = database!!.rawQuery("SELECT * FROM Alarm WHERE hour=$hour AND minute=$minute ", null)
        cursor.moveToFirst()
        return Alarm(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("hour")),
                cursor.getInt(cursor.getColumnIndex("minute")), cursor.getString(cursor.getColumnIndex("message")),
                cursor.getInt(cursor.getColumnIndex("sound")), cursor.getInt(cursor.getColumnIndex("visible")))
    }
}
