package com.example.thebestreminder

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.thebestreminder.ui.theme.TheBestReminderTheme
import java.util.Calendar
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TheBestReminderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BestReminderApp()
                }
            }
        }
    }
}

@Composable
private fun BestReminderApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val preferences = remember(context) { ReminderPreferences(context) }
    val scheduler = remember(context) { ReminderScheduler(context) }
    val initialSettings = remember { preferences.loadSettings() }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var reminderHour by rememberSaveable { mutableIntStateOf(initialSettings.hour) }
    var reminderMinute by rememberSaveable { mutableIntStateOf(initialSettings.minute) }
    var reminderEnabled by rememberSaveable { mutableStateOf(initialSettings.enabled) }
    var pendingPermissionSchedule by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            val pendingSchedule = pendingPermissionSchedule
            pendingPermissionSchedule = null

            if (granted && pendingSchedule != null) {
                scheduler.scheduleReminder(
                    hour = pendingSchedule.first,
                    minute = pendingSchedule.second,
                )
                reminderEnabled = true
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        "Daily reminder set for ${formatReminderTime(context, reminderHour, reminderMinute)}.",
                    )
                }
            } else {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        "Notification permission is required before Android can send the reminder.",
                    )
                }
            }
        }

    fun scheduleReminder() {
        preferences.saveTime(reminderHour, reminderMinute)

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            pendingPermissionSchedule = reminderHour to reminderMinute
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        scheduler.scheduleReminder(reminderHour, reminderMinute)
        reminderEnabled = true
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                "Daily reminder set for ${formatReminderTime(context, reminderHour, reminderMinute)}.",
            )
        }
    }

    fun cancelReminder() {
        scheduler.cancelReminder()
        reminderEnabled = false
        coroutineScope.launch {
            snackbarHostState.showSnackbar("Daily reminder turned off.")
        }
    }

    fun showTimePicker() {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                reminderHour = selectedHour
                reminderMinute = selectedMinute
                preferences.saveTime(selectedHour, selectedMinute)

                if (reminderEnabled) {
                    scheduler.scheduleReminder(selectedHour, selectedMinute)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "Reminder updated to ${formatReminderTime(context, selectedHour, selectedMinute)}.",
                        )
                    }
                }
            },
            reminderHour,
            reminderMinute,
            DateFormat.is24HourFormat(context),
        ).show()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "Daily confidence boost",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "You are the best.",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Pick a time and Android will remind you every day.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Reminder time: ${formatReminderTime(context, reminderHour, reminderMinute)}",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (reminderEnabled) {
                    "Status: daily reminder is on."
                } else {
                    "Status: daily reminder is off."
                },
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showTimePicker() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Choose reminder time")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { scheduleReminder() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Turn on daily reminder")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { cancelReminder() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Turn off reminder")
            }
        }
    }
}

private fun formatReminderTime(context: Context, hour: Int, minute: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }
    return DateFormat.getTimeFormat(context).format(calendar.time)
}

@Preview(showBackground = true)
@Composable
private fun BestReminderPreview() {
    TheBestReminderTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("You are the best.", style = MaterialTheme.typography.headlineMedium)
                Text("Pick a time and Android will remind you every day.")
            }
        }
    }
}
