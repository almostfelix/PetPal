// SettingsScreen.kt
package com.example.jetpackcomposetest

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposetest.ui.theme.JetpackComposeTestTheme

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        SettingItem(title = "Notifications")
        SettingItem(title = "Privacy")
        SettingItem(title = "Account")
    }
}

@Composable
fun SettingItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(checked = false, onCheckedChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    JetpackComposeTestTheme {
        SettingsScreen()
    }
}