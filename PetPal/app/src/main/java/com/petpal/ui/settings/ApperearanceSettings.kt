package com.petpal.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petpal.R
import com.petpal.ui.SettingsCategory

@Composable
fun ApperearanceSettings(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(R.drawable.baseline_brush_128),
                contentDescription = "Appearance Icon",
                modifier = Modifier.size(128.dp),
                tint = colorResource(id = R.color.black_icon)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Appearance",
                modifier = Modifier
                    .padding(16.dp), fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.white))
                    .padding(16.dp)

            ) {
                Column {
                    SettingsCategory(
                        title = "Settings 1",
                        icon = R.drawable.baseline_settings_32,
                        onClick = { /* Handle click */ }
                    )
                    SettingsCategory(
                        title = "Settings 2",
                        icon = R.drawable.baseline_settings_32,
                        onClick = { /* Handle click */ }
                    )
                    SettingsCategory(
                        title = "Settings 3",
                        icon = R.drawable.baseline_settings_32,
                        onClick = { /* Handle click */ }
                    )
                    SettingsCategory(
                        title = "Settings 4",
                        icon = R.drawable.baseline_settings_32,
                        onClick = { /* Handle click */ }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Back")
            }


        }
    }
}