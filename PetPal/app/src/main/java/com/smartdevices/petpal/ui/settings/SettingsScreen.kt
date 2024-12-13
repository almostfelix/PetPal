package com.smartdevices.petpal.ui.settings

import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.petpal.R
import com.smartdevices.petpal.login.LoginActivity
import com.smartdevices.petpal.tools.OnSaveMethodCompleteListener
import com.smartdevices.petpal.ui.theme.JetpackComposeTestTheme
import com.smartdevices.petpal.tools.PreferenceManager


@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)
    val isDarkMode = preferenceManager.getTheme()
    var isChecked by remember { mutableStateOf(isDarkMode) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_32),
                            contentDescription = null,
                            tint = colorResource(id = R.color.black_icon),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.width(60.dp))


                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .width(175.dp)
                            .height(42.dp),

                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(21.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Account Settings",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp,
                                style = TextStyle(fontWeight = FontWeight.ExtraBold),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(90.dp))


            }
            if (preferenceManager.getSaveMethod() == "local") {

                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    onClick = {
                        PreferenceManager(context).setSaveMethod("NOT SET", object :
                            OnSaveMethodCompleteListener {
                            override fun onSaveMethodComplete() {
                                // The callback code to be executed when saving is complete
                                context.startActivity(Intent(context, LoginActivity::class.java))
                            }
                        })
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(50.dp), // Circle size
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_account_circle_128),
                                contentDescription = null,
                                tint = colorResource(id = R.color.black_icon),
                                modifier = Modifier.size(50.dp)
                            )
                        }


                        Spacer(modifier = Modifier.width(16.dp))


                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Guest",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Color.Black
                            )

                            Text(
                                text = "Click to sign in",
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,

                                )
                        }
                    }


                }
            } else if (preferenceManager.getSaveMethod() == "cloud") {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    onClick = {

                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(50.dp), // Circle size
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = preferenceManager.getUserPhoto(),
                                contentDescription = null,
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        }


                        Spacer(modifier = Modifier.width(16.dp))


                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Welcome!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Color.Black
                            )

                            Text(
                                text = preferenceManager.getUserName(),
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,

                                )
                        }
                    }


                }


            }


            /*
            FillerText("General", 16, 0)

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp), // Padding between rows
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_dark_mode_32), // Icon for each setting
                            contentDescription = "Theme",
                            tint = colorResource(id = R.color.l_blue), // Icon color
                            modifier = Modifier.size(24.dp) // Icon size
                        )

                        Spacer(modifier = Modifier.width(16.dp)) // Space between icon and text

                        // Setting name text
                        Text(
                            text = "Theme",
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.black_icon), // Text color
                            modifier = Modifier.weight(1f) // Makes text take up the remaining space
                        )

                        Switch(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = it
                                preferenceManager.setTheme(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorResource(id = R.color.bg),
                                uncheckedThumbColor = Color.Gray,
                                checkedTrackColor = colorResource(id = R.color.l_blue),
                                uncheckedTrackColor = Color.LightGray
                            )
                        )

                    }


                }
            }
            if (preferenceManager.getSaveMethod() == "cloud") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp),

                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(colorResource(id = R.color.g_red))
                    ) {
                        Box(
                            modifier = Modifier

                                .padding(16.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_logout_32),
                                contentDescription = null,
                                tint = colorResource(id = R.color.bg), // Icon color
                                modifier = Modifier
                                    .size(24.dp) // Icon size
                                    .clickable {
                                        preferenceManager.setSaveMethod("local", null)
                                        navController.popBackStack()
                                    }
                            )
                        }
                    }
                }

            }
            */
        }
    }
}

@Composable
fun FillerText(title: String, size: Int, padding: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = size.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            style = TextStyle(fontWeight = FontWeight.ExtraBold),
        )
    }
}


