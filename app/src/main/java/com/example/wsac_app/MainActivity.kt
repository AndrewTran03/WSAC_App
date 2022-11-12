package com.example.wsac_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*

class MainActivity : AppCompatActivity() {

    //Class Variables
    //Website I Used Here for PostHere.io: https://posthere.io/706a-4262-85b5
    companion object {
        const val USERNAME = "CS3714"
        const val USER_ID = "WSAC_APP"
        const val TAG: String = "CS_3714_SEMESTER_PROJECT"
        const val URL = "https://posthere.io/"
        const val ROUTE = "706a-4262-85b5"

        fun appendWorkRequestEvent(event: String) {
            val inputData = Data.Builder().putString("username", MainActivity.USERNAME)
                .putString("date", Calendar.getInstance().time.toString())
                .putString("userID", MainActivity.USER_ID)
                .putString("event", event).build()

            val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            val setUploadWorkRequestWithInputData = uploadWorkRequest.setInputData(inputData).build()
            WorkManager.getInstance().beginUniqueWork(event, ExistingWorkPolicy.REPLACE, setUploadWorkRequestWithInputData)
                .enqueue()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}