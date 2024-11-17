package com.petpal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petpal.R
import com.petpal.tools.PreferenceManager


@Composable
fun LoginScreen(preferenceManager: PreferenceManager, onGoogleSignInClick: () -> Unit, onLocalSignInClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg))
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.logo_with_shadow_3), // Replace with your logo resource
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PetPal",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black_icon)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Explanation Text
            Text(
                text = "Would you like to sign in to save your data on the cloud?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = colorResource(id = R.color.black_icon),
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 24.dp),
        ) {
            // Cloud Save Option
            Button(
                onClick = {
                    onGoogleSignInClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.g_blue))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = R.color.bg),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(4.dp)
                            .align(Alignment.CenterStart),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google_icon), // Replace with Google logo
                            contentDescription = "Google",
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center),
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Sign in with Google",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Local Save Option
            Button(
                onClick = {
                    onLocalSignInClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.accent_dark))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = R.color.accent),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(4.dp)
                            .align(Alignment.CenterStart),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_smartphone_32), // Replace with Google logo
                            contentDescription = "Local save",
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center),
                            tint = colorResource(id = R.color.black_icon)
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "No thanks",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}