package com.example.wsac_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WSACViewModel (application: Application): AndroidViewModel(application) {
    private var loggedIn: Boolean

    init {
        loggedIn = false
    }
}