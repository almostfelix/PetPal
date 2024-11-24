package com.petpal.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.widget.DatePicker
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.petpal.R
import com.petpal.db.Pet
import com.petpal.db.PetViewModel
import com.petpal.ui.theme.JetpackComposeTestTheme
import java.util.Calendar


@Composable
fun AddEventDialog(
    context: Context,
    onDismissRequest: () -> Unit,
    onConfirm: (String, String, String, String, String) -> Unit
) {
    // State holders for your input fields
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var isGeneral by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = {  }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg))
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

                                val dpd = DatePickerDialog(context, { view, year, monthOfYear, dayOfMonth ->
                                    date = "$year-${monthOfYear + 1}-$dayOfMonth"
                                }, year, month, day)
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

                                val tpd = TimePickerDialog(context, { view, hourOfDay, minute ->
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
                        ){
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
                        ){
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}
