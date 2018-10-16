package br.com.cwi.lunchpoker.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.cwi.lunchpoker.LaunchActivity
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.Session
import com.onesignal.OSNotification
import com.onesignal.OneSignal
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(object : OneSignal.NotificationReceivedHandler {
                    override fun notificationReceived(notification: OSNotification) {
                        val data = notification.payload.additionalData

                        var local = data.getString("local")

                        val dialogBuilder = AlertDialog.Builder(applicationContext)
                        val inflater = layoutInflater
                        val dialogView = inflater.inflate(R.layout.view_done, null)
                        val dialog = dialogBuilder.create()

                        var btnOK = dialogView.findViewById<Button>(R.id.btnOK)
                        var txtLocal = dialogView.findViewById<TextView>(R.id.txtLocal)

                        txtLocal.text = Session.getLocal(local)

                        btnOK.setOnClickListener {
                            dialog.dismiss()
                        }

                        dialog.setView(dialogView)
                        dialog.show()

                        Session.isHost = false

                        var tags = mutableListOf<String>()
                        tags.add("sala")
                        OneSignal.deleteTags(tags)
                    }
                })
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        Handler().postDelayed({
            startActivity(Intent(this, LaunchActivity::class.java))
        }, 2000)

    }

    fun showDialog(local: String){
        val dialogBuilder = AlertDialog.Builder(applicationContext)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.view_done, null)
        val dialog = dialogBuilder.create()

        var btnOK = dialogView.findViewById<Button>(R.id.btnOK)
        var txtLocal = dialogView.findViewById<TextView>(R.id.txtLocal)

        txtLocal.text = Session.getLocal(local)

        btnOK.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(dialogView)
        dialog.show()
    }
}
