package com.smartdevices.petpal.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petpal.R
import com.smartdevices.petpal.db.Event
import com.smartdevices.petpal.db.Pet
import com.smartdevices.petpal.db.PetViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventCard(events: List<Event>, currentPet: Pet, petViewModel: PetViewModel) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var editDialogEvent by remember { mutableStateOf<Event?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.bg))
            .padding(16.dp, 0.dp, 16.dp, 16.dp), // Add padding to the column
        verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between cards
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AddEventBtn { showDialog = true }
        //Spacer(modifier = Modifier.height(500.dp))
        events.forEach { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .combinedClickable(
                        onClick = {
                            editDialogEvent = event
                            showDialog = true
                        },
                        onLongClick = { },
                    ),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.bg))
                        .padding(16.dp),
                ) {
                    // First Column
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight() // Allow the column to expand based on content
                            .padding(end = 8.dp), // Add spacing between columns
                    ) {
                        Text(
                            text = event.title,
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.black_icon),
                            modifier = Modifier
                                .wrapContentHeight()
                        )
                        Text(
                            text = event.description,
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.disabled),
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(top = 8.dp) // Add spacing between texts
                        )
                    }

                    // Second Column
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight() // Allow the column to expand based on content
                            .padding(start = 8.dp), // Add spacing between columns
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between rows
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = event.time,
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.black_icon),
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_access_time_32),
                                contentDescription = "Time Icon",
                                modifier = Modifier.size(24.dp),
                                tint = colorResource(id = R.color.prim)
                            )
                        }
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = event.date,
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.black_icon),
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.outline_calendar_month_32),
                                contentDescription = "Calendar Icon",
                                modifier = Modifier.size(24.dp),
                                tint = colorResource(id = R.color.prim)
                            )
                        }
                    }
                }
            }
        }
    }

    CustomDialog(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false },
        content = {
            AddEventDialog(
                context = context,
                onDismissRequest = {event ->
                    showDialog = false
                    editDialogEvent = null
                    if (event.eventId != -1) petViewModel.deleteEventFromPet(petId = currentPet.petId, event = event)},
                onConfirm = { event ->
                    if (event.eventId == -1) petViewModel.addEventToPet(petId = currentPet.petId, event = event)
                    else petViewModel.updateEvent(event = event)
                    showDialog = false
                    editDialogEvent = null
                },
                event = editDialogEvent
            )
        }
    )
}