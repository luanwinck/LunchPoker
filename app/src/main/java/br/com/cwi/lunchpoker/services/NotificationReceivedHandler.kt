package br.com.cwi.lunchpoker.services

import android.util.Log
import android.widget.Toast
import org.json.JSONObject
import com.onesignal.OSNotification
import com.onesignal.OneSignal

class NotificationReceivedHandler : OneSignal.NotificationReceivedHandler {
    override fun notificationReceived(notification: OSNotification) {
        val data = notification.payload.additionalData

        Log.e("NOTIFICACAO", data.getString("local"))
    }
}