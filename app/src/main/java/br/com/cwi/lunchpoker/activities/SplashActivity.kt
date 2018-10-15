package br.com.cwi.lunchpoker.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import br.com.cwi.lunchpoker.NewLocationActivity
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.services.NotificationReceivedHandler
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
                        Toast.makeText(applicationContext,"O local é: "+data.getString("local"),Toast.LENGTH_LONG).show()
                    }
                })
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        val tags = JSONObject()
        tags.put("sala", "205")
        OneSignal.sendTags(tags)

        /*var tags = mutableListOf<String>()
        tags.add("sala")
        OneSignal.deleteTags(tags)*/

        Handler().postDelayed({
            startActivity(Intent(this, NewLocationActivity::class.java))
        }, 2000)

    }
}
