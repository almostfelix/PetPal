package com.smartdevices.petpal.ui

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.petpal.R
import com.smartdevices.petpal.db.Event
import com.smartdevices.petpal.db.Media
import com.smartdevices.petpal.db.Pet
import com.smartdevices.petpal.db.PetViewModel
import com.smartdevices.petpal.tools.PreferenceManager
import com.smartdevices.petpal.ui.theme.JetpackComposeTestTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min
import kotlin.math.pow

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: PetViewModel,
    preferenceManager: PreferenceManager
) {
    viewModel.loadPets()
    viewModel.getThumbnails()
    viewModel.loadBirthDayEvents()
    val thumbnailList: List<Media> by viewModel.thumbnails.collectAsState(initial = emptyList())
    val petsList: List<Pet> by viewModel.petsList.collectAsState(initial = emptyList())
    val birthdayEvents: List<Event> by viewModel.birthDayEvents.collectAsState(initial = emptyList())

    LaunchedEffect(petsList) {
        if (petsList.isNotEmpty()) {
                calculateBirthdayEventsForPets(viewModel, petsList, birthdayEvents)
        }
    }

    Log.d("Debug", thumbnailList.toString())
    JetpackComposeTestTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.bg))
        ) {
            val nextBirthday = birthdayEvents.minByOrNull { LocalDate.parse(it.date) }
            TopAppBarMainScreen(navController = navController,
                birthdayEvent = calculateNextBirthday(petsList, birthdayEvents)
            )
            MainScreenBody(
                pets = petsList,
                thumbnailList = thumbnailList,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

// Function to calculate next birthday
fun calculateNextBirthday(pets: List<Pet>, birthdayEvents: List<Event>): String {
    val today = LocalDate.now()

    // Find the pet with the next birthday
    val nextBirthdayEvent = birthdayEvents
        .filter { it.type == "birthday" }
        .mapNotNull { event ->
            pets.find { it.petId == event.petId }?.let { pet ->
                // Parse the birthday and calculate the next birthday
                val birthDate = LocalDate.parse(pet.birthDate)
                val thisYearBirthday = birthDate.withYear(today.year)

                val nextBirthday = if (thisYearBirthday.isBefore(today)) {
                    thisYearBirthday.plusYears(1)  // if this year's birthday already passed, use next year
                } else {
                    thisYearBirthday
                }

                // Return the pet's name and days to the next birthday
                val daysUntilBirthday = ChronoUnit.DAYS.between(today, nextBirthday)
                pet.name to daysUntilBirthday
            }
        }
        .minByOrNull { it.second }  // Find the pet with the closest birthday

    // If we have a valid next birthday event
    return if (nextBirthdayEvent != null) {
        if (nextBirthdayEvent.second == 0L) {
            val (petName, daysLeft) = nextBirthdayEvent
            "Today is $petName's Birthday!"
        }else {
            val (petName, daysLeft) = nextBirthdayEvent
            "$petName's Birthday in $daysLeft days"
        }
    } else {
        "PetPal"
    }
}

// Function to calculate birthdays and add events
fun calculateBirthdayEventsForPets(
    viewModel: PetViewModel,
    pets: List<Pet>,
    birthdayEvents: List<Event>
) {
    val today = LocalDate.now()

    pets.forEach { pet ->
        Log.d("Debug", "Calculating birthday for ${pet.name}")
        pet.birthDate?.let { birthDate ->
            // Parse the birth date (assuming ISO 8601 format: "yyyy-MM-dd")
            val birthDateParsed = LocalDate.parse(birthDate)
            val thisYearBirthday = birthDateParsed.withYear(today.year)

            // Determine the next birthday
            val nextBirthday = if (thisYearBirthday.isBefore(today)) {
                thisYearBirthday.plusYears(1)
            } else {
                thisYearBirthday
            }

            // Check if event already exists for the pet
            val existingEvent = birthdayEvents.find { it.petId == pet.petId }
            Log.d("Debug", "Birthday event: $existingEvent")
            Log.d("Debug", "Existing event: $existingEvent")

            if (existingEvent != null) {
                if (existingEvent.date != nextBirthday.toString()) {
                    // Delete the old event and create a new one
                    viewModel.deleteEventFromPet(event = existingEvent, pet.petId)
                    viewModel.addEventToPet(
                        event = Event(
                            eventId = -1,
                            petId = pet.petId,
                            title = "Birthday",
                            date = nextBirthday.toString(),
                            description = "${pet.name}'s Birthday!",
                            type = "birthday"
                        ),
                        petId = pet.petId
                    )
                }
            } else {
                // If no event exists, create a new one
                val event = Event(
                    eventId = -1,
                    petId = pet.petId,
                    title = "Birthday",
                    date = nextBirthday.toString(),
                    description = "${pet.name}'s Birthday!",
                    type = "birthday"
                )
                viewModel.addEventToPet(event = event, petId = pet.petId)
            }
        }
    }
}


@Composable
fun MainScreenBody(
    pets: List<Pet>,
    thumbnailList: List<Media>,
    navController: NavController,
    viewModel: PetViewModel
) {
    val isSelectionMode = remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Pet>() }

    // Update `isSelectionMode` based on `selectedItems`
    LaunchedEffect(selectedItems.size) {
        isSelectionMode.value = selectedItems.isNotEmpty()
    }

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
            for (pet in pets) {
                CardMainScreen(
                    pet = pet,
                    thumbnailList = thumbnailList.filter { it.petId == pet.petId },
                    isSelectionMode = isSelectionMode.value,
                    selectedItems = selectedItems,
                    navController = navController
                )
            }
        }
        // Delete button with bounce animation
        val scale by animateFloatAsState(
            targetValue = if (isSelectionMode.value && selectedItems.isNotEmpty()) 1.1f else 1f,
            animationSpec = tween(
                durationMillis = 300, // Duration for the bounce
                easing = { fraction -> 1 - (1 - fraction).pow(2f) } // Easing function for a bounce effect
            )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .scale(scale), // Apply the bounce scale here
                colors = if (isSelectionMode.value && selectedItems.isNotEmpty())
                    CardDefaults.cardColors(containerColor = colorResource(id = R.color.g_red))
                else
                    CardDefaults.cardColors(containerColor = colorResource(id = R.color.ll_blue)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp,
                    pressedElevation = 0.dp
                ),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (isSelectionMode.value && selectedItems.isNotEmpty()) {
                        selectedItems.forEach() {
                            viewModel.deletePet(it.petId)
                        }
                        selectedItems.clear() // Clear selection after deletion
                    } else {
                        navController.navigate("add_pet_screen")
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = if (isSelectionMode.value && selectedItems.isNotEmpty())
                            painterResource(R.drawable.round_delete_forever_32)
                        else
                            painterResource(R.drawable.baseline_add_32),
                        contentDescription = null,
                        tint = colorResource(id = R.color.black_icon),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun TopAppBarMainScreen(navController: NavController, birthdayEvent: String) {
    val context = LocalContext.current
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
                .width(230.dp)
                .height(42.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(21.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
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
                        text = birthdayEvent,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 11.sp,
                        maxLines = 1,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(0.dp, 0.dp, 12.dp, 0.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.Center),
                onClick = { navController.navigate("settings_screen") },
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 0.dp
                ),
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardMainScreen(
    pet: Pet,
    thumbnailList: List<Media>,
    isSelectionMode: Boolean,
    selectedItems: SnapshotStateList<Pet>,
    navController: NavController
) {
    val isSelected = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val borderWidth by animateDpAsState(
            targetValue = if (isSelected.value) 3.dp else 0.dp, // Animate to a wider border when selected and back to 0 when deselected
            animationSpec = tween(durationMillis = 300)
        )

        val borderColor =
            if (borderWidth == 0.dp) Color.Transparent else colorResource(id = R.color.prim)
        Card(
            modifier = Modifier
                .width(350.dp)
                .height(235.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .combinedClickable(
                        onLongClick = {
                            if (!isSelectionMode) {
                                isSelected.value = true
                                selectedItems.add(pet)
                            } else {
                                toggleSelection(pet, isSelected, selectedItems)
                            }
                        },
                        onClick = {
                            if (isSelectionMode) {
                                toggleSelection(pet, isSelected, selectedItems)
                            } else {
                                navController.navigate("pet_ui_screen/${pet.petId}")
                            }
                        }
                    ),
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
                                    text = pet.name,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Text(
                                    text = pet.breed,
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
                            .background(Color.Transparent),
                        /*.innerShadow(
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
                        )*/
                    ) {
                        val painter =
                            rememberAsyncImagePainter(
                                thumbnailList.find { it.petId == pet.petId }?.url
                                    ?: ""
                            )
                        val state = painter.state

                        val transition by animateFloatAsState(
                            targetValue = if (state is AsyncImagePainter.State.Success) 1f else if (state is AsyncImagePainter.State.Loading) 0f else 1f,
                            animationSpec = tween(durationMillis = 300)
                        )
                        Image(
                            painter = painter,
                            contentDescription = "custom transition based on painter state",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .scale(.2f + (.8f * transition))
                                .alpha(min(1f, transition / .2f))
                        )

                        /*
                        AsyncImage(
                            model = thumbnailList.find { it.petId == pet.petId }?.url ?: "",
                            contentDescription = "Selected Pet Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = ColorPainter(colorResource(id = R.color.bg)),
                        )*/

                    }
                }
            }
        }
    }
}

private fun toggleSelection(
    pet: Pet,
    isSelected: MutableState<Boolean>,
    selectedItems: SnapshotStateList<Pet>
) {
    if (isSelected.value) {
        selectedItems.remove(pet)
    } else {
        selectedItems.add(pet)
    }
    isSelected.value = !isSelected.value
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