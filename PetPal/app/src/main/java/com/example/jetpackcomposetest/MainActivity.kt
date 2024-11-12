package com.example.jetpackcomposetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposetest.ui.theme.JetpackComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeTestTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.bg)),
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(12.dp, 12.dp, 0.dp, 6.dp)
                            .height(52.dp),
                    ) {
                        // Align the icon to the start (left)
                        Icon(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterStart),
                            tint = Color.Unspecified
                        )

                        // Center the Card in the Box
                        Card(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(180.dp)
                                .height(50.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_calendar_month_32),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.CenterVertically)
                                    //tint = Color.Unspecified
                                )
                                Text(
                                    text = "Max birthday in 2 days!",
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterEnd)
                                .width(100.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .align(Alignment.CenterVertically),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                                shape = RoundedCornerShape(25.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .fillMaxHeight(),
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_add_32),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.accent_dark),
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center)
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Card(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .align(Alignment.CenterVertically),
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                shape = RoundedCornerShape(25.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .fillMaxHeight(),
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_settings_32),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.black_icon),
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center)
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg)),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp, 12.dp, 0.dp, 6.dp)
                .height(52.dp),
        ) {
            // Align the icon to the start (left)
            Icon(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterStart),
                tint = Color.Unspecified
            )

            // Center the Card in the Box
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(180.dp)
                    .height(50.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_calendar_month_32),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                        //tint = Color.Unspecified
                    )
                    Text(
                        text = "Max birthday in 2 days!",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .width(100.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                    Card(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .align(Alignment.CenterVertically),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .fillMaxHeight(),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_32),
                                contentDescription = null,
                                tint = colorResource(id = R.color.accent_dark),
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.Center)
                            )
                        }

                    }

                Spacer(modifier = Modifier.width(8.dp))

                Card(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .align(Alignment.CenterVertically),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .fillMaxHeight(),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_settings_32),
                            contentDescription = null,
                            tint = colorResource(id = R.color.black_icon),
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center)
                        )
                    }

                }
            }
        }
    }
}
