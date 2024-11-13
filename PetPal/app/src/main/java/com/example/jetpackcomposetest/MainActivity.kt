package com.example.jetpackcomposetest

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur.NORMAL
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposetest.ui.theme.JetpackComposeTestTheme
import com.example.jetpackcomposetest.tools.customOverscroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTestTheme {
                // Set up NavController
                val navController = rememberNavController()
                // NavHost with MainScreen and SettingsScreen
                NavHost(
                    navController = navController,
                    startDestination = "main_screen",
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }
                ) {
                    composable("main_screen") {
                        MainScreen(navController = navController) // Main Screen
                    }
                    composable("settings_screen") {
                        SettingsScreen(navController = navController) // Settings Screen
                    }
                }
            }
        }
    }

    @Preview(showBackground = true, name = "MainScreenPreview")
    @Composable
    fun MainScreenPreview() {
        val navController = rememberNavController()
        MainScreen(navController = navController)
    }

    @Composable
    fun MainScreen(navController: NavController) {
        JetpackComposeTestTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.bg)),
                verticalArrangement = Arrangement.Top
            ) {
                TopAppBarMainScreen(navController = navController)
                MainScreenBody()
            }
        }
    }

    @Composable
    fun MainScreenBody() {

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(0.dp, 16.dp, 0.dp, 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                CardMainScreen()
                CardMainScreen()
                CardMainScreen()
                CardMainScreen()
                CardMainScreen()
            }
        }

    }

    @Composable
    fun TopAppBarMainScreen(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.bg))
                .padding(12.dp, 12.dp, 0.dp, 0.dp)
                .height(52.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.logo_with_shadow_3),
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
                    .height(42.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(21.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_calendar_month_32),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                        //tint = Color.Unspecified
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Max birthday in 2 days!",
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 11.sp
                        )
                    }
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
                        .align(Alignment.CenterVertically)
                        .clickable { navController.navigate("settings_screen") },
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

    @Composable
    fun CardMainScreen() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .height(235.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left column for text information, taking up more space
                            Column(
                                modifier = Modifier
                                    .weight(1f) // Occupies most of the row width
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    Text(
                                        text = "Max",
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    Text(
                                        text = "Labrador",
                                        fontSize = 12.sp,
                                    )
                                }
                            }

                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_forward_ios_32),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp))
                                .background(colorResource(id = R.color.prim))
                                .innerShadow(
                                    color = Color.Black,
                                    cornersRadius = 0.dp,
                                    spread = 3.dp,
                                    blur = 16.dp,
                                    offsetY = 1.dp,
                                    offsetX = 0.dp,
                                    shadowTop = true,
                                    shadowBottom = false,
                                    shadowLeft = false,
                                    shadowRight = false
                                ),
                        ) {

                        }
                    }
                }
            }
        }
    }

    fun Modifier.innerShadow(
        color: Color = Color.Black,
        cornersRadius: Dp = 0.dp,
        spread: Dp = 0.dp,
        blur: Dp = 0.dp,
        offsetY: Dp = 0.dp,
        offsetX: Dp = 0.dp,
        shadowTop: Boolean = true,
        shadowBottom: Boolean = true,
        shadowLeft: Boolean = true,
        shadowRight: Boolean = true
    ) = drawWithContent {

        drawContent()

        val rect = Rect(Offset.Zero, size)
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        drawIntoCanvas { canvas ->

            // Setup basic paint properties
            paint.color = color
            paint.isAntiAlias = true
            frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

            if (blur.toPx() > 0) {
                frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }

            // Adjust shadow position by offsetX and offsetY, then draw shadows for each side
            if (shadowTop) {
                canvas.drawRoundRect(
                    left = rect.left + spread.toPx() / 2,
                    top = rect.top - spread.toPx() + offsetY.toPx(),
                    right = rect.right - spread.toPx() / 2,
                    bottom = rect.top + offsetY.toPx(),
                    radiusX = cornersRadius.toPx(),
                    radiusY = cornersRadius.toPx(),
                    paint
                )
            }

            if (shadowBottom) {
                canvas.drawRoundRect(
                    left = rect.left + spread.toPx() / 2,
                    top = rect.bottom + offsetY.toPx(),
                    right = rect.right - spread.toPx() / 2,
                    bottom = rect.bottom + spread.toPx() + offsetY.toPx(),
                    radiusX = cornersRadius.toPx(),
                    radiusY = cornersRadius.toPx(),
                    paint
                )
            }

            if (shadowLeft) {
                canvas.drawRoundRect(
                    left = rect.left - spread.toPx() + offsetX.toPx(),
                    top = rect.top + spread.toPx() / 2,
                    right = rect.left + spread.toPx() + offsetX.toPx(),
                    bottom = rect.bottom - spread.toPx() / 2,
                    radiusX = cornersRadius.toPx(),
                    radiusY = cornersRadius.toPx(),
                    paint
                )
            }

            if (shadowRight) {
                canvas.drawRoundRect(
                    left = rect.right + offsetX.toPx(),
                    top = rect.top + spread.toPx() / 2,
                    right = rect.right + spread.toPx() + offsetX.toPx(),
                    bottom = rect.bottom - spread.toPx() / 2,
                    radiusX = cornersRadius.toPx(),
                    radiusY = cornersRadius.toPx(),
                    paint
                )
            }

            // Clean up
            frameworkPaint.xfermode = null
            frameworkPaint.maskFilter = null
        }
    }



}
