package com.petpal.ui

import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.petpal.R
import com.petpal.login.LoginActivity
import com.petpal.tools.OnSaveMethodCompleteListener
import com.petpal.tools.PreferenceManager
import com.petpal.ui.theme.JetpackComposeTestTheme

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)

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
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = colorResource(id = R.color.shadow)
                )

                if(preferenceManager.getSaveMethod() == "cloud")
                    SettingItem(
                        title = "Log Out",
                        icon = R.drawable.baseline_logout_32,
                        onClick = { preferenceManager.setSaveMethod("local",null)
                                  navController.popBackStack()},
                        textcolor = R.color.g_red,
                        iconcolor = Color.Red
                    )
                else{
                    SettingItem(
                        title = "Log in",
                        icon = R.drawable.baseline_login_32,
                        onClick = { PreferenceManager(context).setSaveMethod("NOT SET", object :
                            OnSaveMethodCompleteListener {
                            override fun onSaveMethodComplete() {
                                // The callback code to be executed when saving is complete
                                context.startActivity(Intent(context, LoginActivity::class.java))
                            }
                        }) },
                        textcolor = R.color.g_blue,
                        iconcolor = colorResource(id = R.color.g_blue)
                    )
                }

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

@Composable
fun SettingItem(title: String, icon: Int, onClick: () -> Unit, textcolor : Int, iconcolor : Color) {
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
                tint = iconcolor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                color = colorResource(id = textcolor),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

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