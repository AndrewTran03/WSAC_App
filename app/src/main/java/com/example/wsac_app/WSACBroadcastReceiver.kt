package com.example.wsac_app

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context

class WSACBroadcastReceiver(private var listFragment: ListFragment? = null) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appName = intent.getStringExtra(MainActivity.APP_NAME)
        listFragment!!.updateStatus(appName!!)
    }
}