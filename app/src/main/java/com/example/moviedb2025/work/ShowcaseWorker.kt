package com.example.moviedb2025.work

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.os.Handler
import android.os.Looper


class ShowcaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        //Show a Toast (and in log)
        Log.d("ShowcaseWorker", "WorkManager is running!")
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                applicationContext,
                "This is my Toast message!",
                Toast.LENGTH_LONG
            ).show()
        }

        return Result.success()
    }
}
