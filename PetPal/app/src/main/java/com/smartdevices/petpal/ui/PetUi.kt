package com.smartdevices.petpal.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petpal.R
import com.smartdevices.petpal.db.Event
import com.smartdevices.petpal.db.Pet
import com.smartdevices.petpal.db.PetViewModel
import java.time.YearMonth




@Composable
fun PetUi(navController: NavController, petId: Int, petViewModel: PetViewModel) {
    petViewModel.getPetById(petId)
    petViewModel.loadEventsForPet(petId)
    val currentPet: Pet? by petViewModel.currentPet.collectAsState(initial = null)
    val eventsList: List<Event> by petViewModel.eventsList.collectAsState(initial = emptyList())
    val mediaList by petViewModel.mediaList.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // State to track the selected tab
    var previousTab by remember { mutableStateOf(0) } // State to track the previous tab

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg)),
    ) {
        currentPet?.let {
            TopAppBarPetUiScreen(navController, it)
            UpperNavigationBar(onTabSelected = { newTab ->
                previousTab = selectedTab // Update the previous tab
                selectedTab = newTab      // Update the selected tab
            })

            // Animated Content for sliding animation
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    if (targetState > previousTab) {
                        // Sliding to the right
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 600, // Slower animation (600ms)
                                easing = FastOutSlowInEasing // Smooth easing
                            )
                        ) togetherWith  slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                    } else {
                        // Sliding to the left
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(
                                durationMillis = 600, // Slower animation (600ms)
                                easing = FastOutSlowInEasing
                            )
                        ) togetherWith  slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { targetTab ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp)
                ) {
                    when (targetTab) {
                        0 -> { // Events Tab
                            item {
                                CalendarScreen(
                                    it,
                                    eventsList,
                                    mediaList,
                                    currentMonth,
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
                                EventCard(eventsList)
                            }
                        }

                        1 -> { // Medical Records Tab
                            item {
                                Text(
                                    text = "Medical Records",
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        2 -> { // Memories Tab
                            item {
                                Text(
                                    text = "Memories",
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        3 -> { // Details Tab
                            item {
                                Text(
                                    text = "Details",
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
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
                onDismissRequest = { showDialog = false },
                onConfirm = { title, description, date, time, type ->
                    currentPet?.let { Event(-1, -1, title, description, date, time, type) }?.let {
                        petViewModel.addEventToPet(petId,
                            it
                        )
                    }
                    showDialog = false
                }
            )
        }
    )
}


@Composable
fun TopAppBarPetUiScreen(navController: NavController, pet: Pet) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.bg))
            .padding(12.dp, 24.dp, 16.dp, 0.dp)
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
fun UpperNavigationBar(onTabSelected: (Int) -> Unit) {
    val items = listOf("Events", "Medical Records", "Memories", "Details")
    val selectedItem = remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = colorResource(id = R.color.bg),
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.height(64.dp),
                icon = {
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
                    Text(
                        text = item,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                },
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    onTabSelected(index) // Notify parent about the selection
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.prim),
                    selectedTextColor = colorResource(R.color.black_icon),
                    indicatorColor = colorResource(R.color.prim_sec)
                ),
            )
        }
    }
}


