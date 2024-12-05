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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
            .height(500.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title
            Text(
                text = "Add Event",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Input Fields
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description Input
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date Selector
                Button(
                    onClick = {
                        val c = Calendar.getInstance()
                        val year = c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)

                        val dpd = DatePickerDialog(
                            context,
                            { view, year, monthOfYear, dayOfMonth ->
                                date = "$year-${monthOfYear + 1}-$dayOfMonth"
                            },
                            year,
                            month,
                            day
                        )
                        dpd.show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = colorResource(id = R.color.prim),
                        contentColor = colorResource(id = R.color.bg),
                        disabledContainerColor = colorResource(id = R.color.prim),
                        disabledContentColor = colorResource(id = R.color.bg),
                    )
                ) {
                    Text("Select Date")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Time Selector
                Button(
                    onClick = {
                        val c = Calendar.getInstance()
                        val hour = c.get(Calendar.HOUR_OF_DAY)
                        val minute = c.get(Calendar.MINUTE)

                        val tpd =
                            TimePickerDialog(context, { view, hourOfDay, minute ->
                                time = String.format("%02d:%02d", hourOfDay, minute)
                            }, hour, minute, true)
                        tpd.show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = colorResource(id = R.color.prim),
                        contentColor = colorResource(id = R.color.bg),
                        disabledContainerColor = colorResource(id = R.color.prim),
                        disabledContentColor = colorResource(id = R.color.bg),
                    )
                ) {
                    Text("Select Time")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SelectableCard(
                        text = "General",
                        isSelected = isGeneral,
                        onClick = { isGeneral = true },
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
                        onClick = { isGeneral = false },
                        modifier = Modifier.weight(1f),
                        selectedColor = colorResource(id = R.color.prim),
                        unselectedColor = colorResource(id = R.color.bg),
                        selectedTextColor = colorResource(id = R.color.bg),
                        unselectedTextColor = colorResource(id = R.color.prim),
                        borderColor = colorResource(id = R.color.prim)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonColors(
                        containerColor = colorResource(id = R.color.prim),
                        contentColor = colorResource(id = R.color.bg),
                        disabledContainerColor = colorResource(id = R.color.prim),
                        disabledContentColor = colorResource(id = R.color.bg),
                    )
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = { onConfirm(title, description, date, time, type) },
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
