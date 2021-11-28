package com.compilinghappen.fitnessandfinance.pedometer

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.compilinghappen.fitnessandfinance.MainActivity
import com.compilinghappen.fitnessandfinance.R

class PedometerService : Service(), SensorEventListener {
    companion object {
        var NUMBER_OF_STEPS = 0
        private const val NOTIFICATION_ID = 1231156
    }

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null
    //private lateinit var sensorManager: SensorManager

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            val intent = Intent(baseContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent, 0)

            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                while (true) {
                    ++NUMBER_OF_STEPS
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }

        /*sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)*/
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        //sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //NUMBER_OF_STEPS = (event?.values!![0]*100).toInt()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

}