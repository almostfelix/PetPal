package com.smartdevices.petpal.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import android.view.Window
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.petpal.R
import java.util.Calendar


@Composable
fun AddEventDialog(
    context: Context,
    onDismissRequest: () -> Unit,
    onConfirm: (String, String, String, String, String) -> Unit,
) {
    // State holders for your input fields
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var isGeneral by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                // Title
                Text(
                    text = "New Event",
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input Fields
            Column {
                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.black_icon),
                        unfocusedTextColor = colorResource(R.color.black_icon),
                        errorIndicatorColor = Color.Transparent,
                        focusedLabelColor = colorResource(R.color.black_icon),
                        unfocusedLabelColor = colorResource(R.color.black_icon),
                        errorLabelColor = colorResource(R.color.error_red),
                        errorContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description Input
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.black_icon),
                        unfocusedTextColor = colorResource(R.color.black_icon),
                        errorIndicatorColor = Color.Transparent,
                        focusedLabelColor = colorResource(R.color.black_icon),
                        unfocusedLabelColor = colorResource(R.color.black_icon),
                        errorLabelColor = colorResource(R.color.error_red),
                        errorContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.black_icon),
                        unfocusedTextColor = colorResource(R.color.black_icon),
                        errorIndicatorColor = Color.Transparent,
                        focusedLabelColor = colorResource(R.color.black_icon),
                        unfocusedLabelColor = colorResource(R.color.black_icon),
                        errorLabelColor = colorResource(R.color.error_red),
                        errorContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_calendar_month_32),
                            contentDescription = "Date Picker",
                            tint = colorResource(id = R.color.prim),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    val c = Calendar.getInstance()
                                    val year = c.get(Calendar.YEAR)
                                    val month = c.get(Calendar.MONTH)
                                    val day = c.get(Calendar.DAY_OF_MONTH)

                                    DatePickerDialog(
                                        context,
                                        { _, year, month, day ->
                                            date = String.format("%04d-%02d-%02d", year, month + 1, day)
                                            // Update the date field
                                        },
                                        year,
                                        month,
                                        day
                                    ).show()
                                }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.black_icon),
                        unfocusedTextColor = colorResource(R.color.black_icon),
                        errorIndicatorColor = Color.Transparent,
                        focusedLabelColor = colorResource(R.color.black_icon),
                        unfocusedLabelColor = colorResource(R.color.black_icon),
                        errorLabelColor = colorResource(R.color.error_red),
                        errorContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_time_32),
                            contentDescription = "Time Picker",
                            tint = colorResource(id = R.color.prim),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    val c = Calendar.getInstance()
                                    val hour = c.get(Calendar.HOUR_OF_DAY)
                                    val minute = c.get(Calendar.MINUTE)

                                    TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minute ->
                                            time = "$hourOfDay:$minute"
                                            // Update the time field
                                        },
                                        hour,
                                        minute,
                                        true
                                    ).show()
                                }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier,
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 4.dp, 8.dp, 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        SelectableCard(
                            text = "General",
                            isSelected = isGeneral,
                            onClick = {
                                isGeneral = true
                                type = "General"
                            },
                            modifier = Modifier.weight(1f),
                            selectedColor = colorResource(id = R.color.prim),
                            unselectedColor = colorResource(id = R.color.bg),
                            selectedTextColor = colorResource(id = R.color.bg),
                            unselectedTextColor = colorResource(id = R.color.prim),
                            borderColor = colorResource(id = R.color.prim)
                        )
                        SelectableCard(
                            text = "Medical",
                            isSelected = !isGeneral,
                            onClick = {
                                isGeneral = false
                                type = "Medical"
                            },
                            modifier = Modifier.weight(1f),
                            selectedColor = colorResource(id = R.color.prim),
                            unselectedColor = colorResource(id = R.color.bg),
                            selectedTextColor = colorResource(id = R.color.bg),
                            unselectedTextColor = colorResource(id = R.color.prim),
                            borderColor = colorResource(id = R.color.prim)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonColors(
                        containerColor = colorResource(id = R.color.g_red),
                        contentColor = colorResource(id = R.color.bg),
                        disabledContainerColor = colorResource(id = R.color.prim),
                        disabledContentColor = colorResource(id = R.color.bg),
                    )
                ) {
                    Text("Cancel")
                }
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        onConfirm(title, description, date, time, type)
                        onDismissRequest()
                    },
                    colors = ButtonColors(
                        containerColor = colorResource(id = R.color.prim),
                        contentColor = colorResource(id = R.color.bg),
                        disabledContainerColor = colorResource(id = R.color.prim),
                        disabledContentColor = colorResource(id = R.color.bg),
                    )
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
    anim: String = ""
) {

    var showAnimatedDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        if (showDialog) showAnimatedDialog = true
    }

    if (showAnimatedDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            val dialogWindow = getDialogWindow()

            SideEffect {
                dialogWindow.let { window ->
                    window?.setDimAmount(0f)
                    window?.setWindowAnimations(-1)
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                var animateIn by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { animateIn = true }
                AnimatedVisibility(
                    visible = animateIn && showDialog,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .pointerInput(Unit) { detectTapGestures { onDismissRequest() } }
                            .background(Color.Black.copy(alpha = .56f))
                            .fillMaxSize()
                    )
                }
                AnimatedVisibility(
                    visible = animateIn && showDialog,
                    enter = if (anim == "bounce") {
                        fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
                            initialScale = .1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        )
                    } else {
                        slideInVertically { it / 2 } + fadeIn() + scaleIn(initialScale = 1f)
                    },
                    exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f)
                ) {
                    Box(
                        Modifier
                            .pointerInput(Unit) { detectTapGestures { } }
                            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                            .width(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                colorResource(id = R.color.bg),
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        content()
                    }

                    DisposableEffect(Unit) {
                        onDispose {
                            showAnimatedDialog = false
                        }
                    }
                }
            }
        }
    }
}


@ReadOnlyComposable
@Composable
fun getDialogWindow(): Window? = (LocalView.current.parent as? DialogWindowProvider)?.window
