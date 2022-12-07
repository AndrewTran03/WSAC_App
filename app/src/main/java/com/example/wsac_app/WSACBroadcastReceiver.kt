package com.example.wsac_app

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.widget.Toast

class WSACBroadcastReceiver(private var mainActivity: MainActivity? = null) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        MainActivity.appendWorkRequestEvent("WSAC BROADCAST RECEIVER ENACTED - ON_RECEIVE() FUNCTION CALLED")
        val appName: String = intent.getStringExtra(MainActivity.APP_NAME)!!
        mainActivity?.updateStatus(appName)
    }
}