package com.example.wsac_app

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.json.JSONObject
import retrofit2.Call

class UploadWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val json = JSONObject()
        json.put("username", inputData.getString("username"))
        json.put("date", inputData.getString("date"))
        json.put("userID", inputData.getString("userID"))
        json.put("event", inputData.getString("event"))

        Log.d(MainActivity.TAG, " PARAMS: ${json.toString()}; URL: " + MainActivity.URL)
        return uploadLog(json, MainActivity.URL)
    }

    private fun uploadLog(json: JSONObject, url: String): Result {
        Log.d(MainActivity.TAG, " URL OF uploadLog(): $url")
        val call: Call<String> = TrackerRetrofitService.create(url).postLog(json)
        val response = call.execute()

        if(response.isSuccessful) {
            return Result.success()
        } else {
            if(response.code() in (500..599)) {
                return Result.retry()
            }

            Log.d(MainActivity.TAG, " RESPONSE IS: $response")
            return Result.failure()
        }
    }
}