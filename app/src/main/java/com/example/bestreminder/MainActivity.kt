package com.example.bestreminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bestreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                enableDailyReminder()
            } else {
                Toast.makeText(this, getString(R.string.permission_denied_msg), Toast.LENGTH_LONG)
                    .show()
                updateStatus()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enableReminderButton.setOnClickListener {
            requestPermissionAndSchedule()
        }

        updateStatus()
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }

    private fun requestPermissionAndSchedule() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }

        enableDailyReminder()
    }

    private fun enableDailyReminder() {
        ReminderScheduler.scheduleDailyReminder(this)
        Toast.makeText(this, getString(R.string.reminder_enabled_toast), Toast.LENGTH_SHORT).show()
        updateStatus()
    }

    private fun updateStatus() {
        val statusText = if (ReminderScheduler.isReminderEnabled(this)) {
            getString(R.string.reminder_status_on)
        } else {
            getString(R.string.reminder_status_off)
        }
        binding.reminderStatusText.text = statusText
    }
}
