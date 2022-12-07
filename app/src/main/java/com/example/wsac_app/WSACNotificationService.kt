package com.example.wsac_app

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class WSACNotificationService : Service() {

    //Class Variables
    private val iBinder = MyBinder()
    private val CHANNEL_ID = "WSAC Channel ID"

    inner class MyBinder: Binder(){
        fun getService(): WSACNotificationService {
            return this@WSACNotificationService
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        return iBinder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        MainActivity.appendWorkRequestEvent("WSAC NOTIFICATION SERVICE ENACTED - ON_START_COMMAND() " +
            "AND CREATE_NOTIFICATION_CHANNEL() FUNCTIONS CALLED")

        this.createNotificationChannel()

        val pendingIntent: PendingIntent = Intent(this, SubmissionsFragment::class.java).let {
            notificationIntent -> PendingIntent.getActivity(this,0,notificationIntent,FLAG_IMMUTABLE)
        }

        val notification: Notification = Notification.Builder(this, CHANNEL_ID).
            setContentTitle("WSAC-App").setContentText("WSAC-App Currently Running").
            setContentIntent(pendingIntent).build()

        startForeground(123, notification)
        return Service.START_STICKY
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(CHANNEL_ID, "Music Service",
            NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(NotificationManager::class.java)
        manager!!.createNotificationChannel(serviceChannel)
    }
}
