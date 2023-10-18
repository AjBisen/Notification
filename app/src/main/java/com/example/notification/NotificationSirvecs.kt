package com.example.notification

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.Notification.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class NotificationServices : FirebaseMessagingService() {
    lateinit var notificationManager: NotificationManager
    private val channelId = "plumberPowerID"
    private val description = "Plumber_power_notifications"
    private var notificationNumber: TextView? = null


    private val MAX_NUMBER = 99
    private val MIN_NUMBER = 0
    private var notification_number_counter = 0
    companion object {
        var badgescounter = 0
    }

    fun notificationcount(view: View) {
        // finding textview through id textview
        // in notification number variable
        notificationNumber = view.findViewById(R.id.text1)
    }

    // increment method
    fun increment() {

        // checking condition if notification_counter-number
        // is greater than max number or not
        if (notification_number_counter > MAX_NUMBER) {
            // printing message maximum number reached
            Log.d("Counter", "Maximum number reached")
        } else {
            // if condition fails then increment the count by 1
            notification_number_counter++
            // returning increased value
            notificationNumber!!.text = notification_number_counter.toString()
        }
    }

    // decrement method
    fun decrement() {

        // checking condition if notification_number_count
        // is less than min number or not
        if (notification_number_counter <= MIN_NUMBER) {
            // if true then message minimum number reached
            Log.d("Counter", "Minimum number reached")
        } else {
            // decrease if condition fails
            notification_number_counter--
            // returning decrease count
            notificationNumber!!.text = notification_number_counter.toString()
        }
    }

    // rest method
    fun reset() {
        // checking if notification_number_count
        // is already zero or not
        if (notification_number_counter == 0) {
            // if true message already zero
            Log.d("alert", " notification count is already 0 ")
        } else {
            // else setting count to zero
            notification_number_counter = 0
            // returning updated value
            notificationNumber!!.text = notification_number_counter.toString()
        }
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        badgescounter = badgescounter + 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = "test"
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

        try {

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            showNotification(notificationManager, this, remoteMessage)
        } catch (e: Exception) {
            Log.d("errornotification", e.localizedMessage)
        }

    }

    private fun showNotification(
        notificationManager: NotificationManager,
        context: Context,
        remoteMessage: RemoteMessage
    ) {
        val context = PlumberPowerApplication.instance
        val intent = Intent(this, MainActivity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //val pintent=PendingIntent.getService(this,1,intent,PendingIntent.FLAG_IMMUTABLE)
        val penIntent = PendingIntent.getActivities(
            this,
            1,
            arrayOf(intent),
            PendingIntent.FLAG_IMMUTABLE,
            null
        )
        val notification = NotificationCompat.Builder(this, channelId)
        notification.setSmallIcon(R.drawable.btn_plus)
            .setContentTitle(remoteMessage.notification!!.title.toString())
            .setContentText(remoteMessage.notification!!.body.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(penIntent)
            .setAutoCancel(true)
        notificationManager.notify(1, notification.build())

    }
}