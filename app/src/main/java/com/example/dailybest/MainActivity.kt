package com.example.dailybest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                enableDailyReminder()
            } else {
                statusText.text = getString(R.string.permission_required_message)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ReminderWorker.createNotificationChannel(this)

        statusText = findViewById(R.id.textStatus)
        val enableButton: Button = findViewById(R.id.buttonEnableReminder)
        enableButton.setOnClickListener {
            requestPermissionAndSchedule()
        }
    }

    private fun requestPermissionAndSchedule() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }
        enableDailyReminder()
    }

    private fun enableDailyReminder() {
        ReminderScheduler.scheduleDailyReminder(this)
        statusText.text = getString(R.string.reminder_enabled_message)
        Toast.makeText(this, R.string.reminder_enabled_toast, Toast.LENGTH_SHORT).show()
    }
}
