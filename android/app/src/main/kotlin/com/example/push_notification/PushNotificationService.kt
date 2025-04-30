package com.example.push_notification

import android.app.*
import android.content.Intent
import android.os.*
import android.widget.Toast
import androidx.core.app.NotificationCompat

class PushNotificationService : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var serviceLooper: Looper
    private lateinit var serviceHandler: Handler

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            try {
                Thread.sleep(5000)
                Toast.makeText(applicationContext, "Service Running", Toast.LENGTH_SHORT).show()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun onCreate() {
        val thread = HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        serviceLooper = thread.looper
        serviceHandler = ServiceHandler(serviceLooper)

        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        val msg = serviceHandler.obtainMessage()
        msg.arg1 = startId
        serviceHandler.sendMessage(msg)
        return START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}