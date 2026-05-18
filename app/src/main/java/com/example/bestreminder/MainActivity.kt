package com.example.bestreminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bestreminder.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var selectedHour = ReminderScheduler.defaultHour
    private var selectedMinute = ReminderScheduler.defaultMinute

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                ReminderScheduler.scheduleReminder(this, selectedHour, selectedMinute)
                renderState()
            } else {
                Toast.makeText(
                    this,
                    R.string.notifications_permission_denied,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val (savedHour, savedMinute) = ReminderScheduler.loadReminderTime(this)
        selectedHour = savedHour
        selectedMinute = savedMinute

        binding.chooseTimeButton.setOnClickListener {
            showTimePicker()
        }

        binding.toggleReminderButton.setOnClickListener {
            if (ReminderScheduler.isReminderEnabled(this)) {
                ReminderScheduler.cancelReminder(this)
                renderState()
            } else {
                enableReminder()
            }
        }

        renderState()
    }

    override fun onResume() {
        super.onResume()
        renderState()
    }

    private fun enableReminder() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        ReminderScheduler.scheduleReminder(this, selectedHour, selectedMinute)
        renderState()
    }

    private fun showTimePicker() {
        val is24Hour = DateFormat.is24HourFormat(this)
        android.app.TimePickerDialog(
            this,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                selectedHour = hourOfDay
                selectedMinute = minute
                ReminderScheduler.saveReminderTime(this, selectedHour, selectedMinute)

                if (ReminderScheduler.isReminderEnabled(this)) {
                    ReminderScheduler.scheduleReminder(this, selectedHour, selectedMinute)
                }

                renderState()
            },
            selectedHour,
            selectedMinute,
            is24Hour
        ).apply {
            setTitle(R.string.time_picker_title)
        }.show()
    }

    private fun renderState() {
        val displayTime = formatTime(selectedHour, selectedMinute)
        val enabled = ReminderScheduler.isReminderEnabled(this)

        binding.selectedTimeValue.text = getString(R.string.selected_time_value, displayTime)
        binding.reminderStatusValue.text = if (enabled) {
            getString(R.string.status_enabled, displayTime)
        } else {
            getString(R.string.status_disabled)
        }
        binding.toggleReminderButton.text = if (enabled) {
            getString(R.string.turn_off_button)
        } else {
            getString(R.string.turn_on_button)
        }
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        return DateFormat.getTimeFormat(this).format(calendar.time)
    }
}
