package com.petpal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.petpal.ui.theme.JetpackComposeTestTheme

@Composable
fun SettingsScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg)),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                    shape = RoundedCornerShape(25.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .fillMaxHeight(),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_32),
                            contentDescription = null,
                            tint = colorResource(id = R.color.black_icon),
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center)
                        )
                    }

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

            ) {
                SettingsCategory(
                    title = "Account Information",
                    icon = R.drawable.baseline_account_circle_32,
                    onClick = { navController.navigate("settings/account_settings") }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = colorResource(id = R.color.shadow)
                )

                SettingsCategory(
                    title = "Notifications",
                    icon = R.drawable.baseline_circle_notifications_32,
                    onClick = { navController.navigate("settings/notif_settings") }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = colorResource(id = R.color.shadow)
                )

                SettingsCategory(
                    title = "Appearance",
                    icon = R.drawable.baseline_brush_32,
                    onClick = { navController.navigate("settings/appear_settings") }

                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp), thickness = 1.dp,
                    color = colorResource(id = R.color.shadow)
                )

                SettingsCategory(
                    title = "Privacy and Security",
                    icon = R.drawable.baseline_gpp_good_32,
                    onClick = { navController.navigate("settings/privacy_settings") }
                )
            }
        }
    }
}

@Composable
fun SettingsCategory(title: String, icon: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),

    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {

            Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(32.dp)
        )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Icon(
            painter = painterResource(R.drawable.baseline_arrow_forward_ios_32),
            contentDescription = null,
            modifier = Modifier.size(24.dp).align(Alignment.CenterEnd))


    }
}



@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    JetpackComposeTestTheme {
        SettingsScreen(navController = navController)
    }
}