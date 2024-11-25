package com.petpal.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.petpal.R
import com.petpal.db.Event
import com.petpal.db.Pet
import com.petpal.db.PetViewModel


@Composable
fun PetUi(navController: NavController, petId: Int, petViewModel: PetViewModel) {
    val petsList by petViewModel.petsList.observeAsState(emptyList())
    val currentPet = petsList.find { it.id == petId }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg))
            .padding(16.dp),
    ) {
        currentPet?.let { pet ->
            TopAppBarPetUiScreen(navController, pet)
            UpperNavigationBar()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    CalendarScreen(
                        petId = pet.id,
                        pets = petsList,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(16.dp)
                    )
                }

                item {
                    AddEventBtn { showDialog = true }
                }
                item {
                    EventCard(pet.events) // Pass the specific pet's events
                }
                // Add more items if needed
            }


        }
    }

    if (showDialog) {
        AddEventDialog(
            context = context,
            onDismissRequest = { showDialog = false },
            onConfirm = { title, description, date, time, type ->
                petViewModel.addEventToPet(petId, Event(title, description, date, time, type))
                showDialog = false
            }
        )
    }
}


@Composable
fun TopAppBarPetUiScreen(navController: NavController, pet: Pet) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.bg))
            .padding(12.dp, 12.dp, 16.dp, 0.dp)
            .height(52.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left Card
        Card(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 0.dp
            ),
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

        // Middle Card
        Card(
            modifier = Modifier
                .weight(1f) // Allow this card to take up remaining space
                .height(42.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(21.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pet.name,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }

        // Right Card
        Card(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 0.dp
            ),
            shape = RoundedCornerShape(25.dp),
            onClick = { }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_edit_32),
                    contentDescription = null,
                    tint = colorResource(id = R.color.prim),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun UpperNavigationBar() {
    val items = listOf("Events", "Medical Records", "Memories", "Details")
    val selectedItem = remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = colorResource(id = R.color.bg),
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        // Iterate over the items and create a NavigationBarItem for each
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.height(64.dp),
                icon = {
                    // Use Row to ensure icon and label stay aligned
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        when (item) {
                            "Events" -> Icon(
                                painter = painterResource(R.drawable.outline_calendar_month_32),
                                contentDescription = item,
                                modifier = Modifier.size(24.dp)
                            )

                            "Medical Records" -> Icon(
                                painter = painterResource(R.drawable.outline_medical_services_32),
                                contentDescription = item,
                                modifier = Modifier.size(24.dp)
                            )

                            "Memories" -> Icon(
                                painter = painterResource(R.drawable.baseline_favorite_border_32),
                                contentDescription = item,
                                modifier = Modifier.size(24.dp)
                            )

                            "Details" -> Icon(
                                painter = painterResource(R.drawable.outline_info_32),
                                contentDescription = item,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                label = {
                    // Center the text label and allow it to wrap if needed
                    Text(
                        text = item,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                },
                selected = selectedItem.value == index,
                onClick = { selectedItem.value = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.prim),
                    selectedTextColor = colorResource(R.color.black_icon),
                    indicatorColor = colorResource(R.color.prim_sec)
                ),
            )
        }
    }
}

