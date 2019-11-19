package alarmnews

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.semiheken.alarms.R

class ToastWhite(Main: Context) {
    private val toast: Toast
    private val inflater: LayoutInflater? = null
    private var txt: TextView? = null
    private val view: View

    init {
        val inflater = Main.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        toast = Toast(Main)
        view = inflater.inflate(R.layout.toast_white, null)
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, -700)
    }

    fun Show() {
        toast.show()
    }

    fun setText(str: String) {
        txt = view.findViewById(R.id.textToast)
        txt!!.text = str
    }

    fun setImage() {
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.alarm)
    }
}
