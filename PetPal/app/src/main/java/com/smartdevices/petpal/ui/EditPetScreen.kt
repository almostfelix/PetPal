package com.smartdevices.petpal.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.datepicker.MaterialDatePicker
import com.petpal.R
import com.smartdevices.petpal.db.Media
import com.smartdevices.petpal.db.Pet
import com.smartdevices.petpal.db.PetViewModel
import com.smartdevices.petpal.tools.ImageUtils
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import kotlin.math.min


@Composable
fun EditPetScreen(
    petViewModel: PetViewModel,
    petId: Int = -1,
    navController: NavController,
    edit: Boolean = true
) {
    val currentContext = LocalContext.current
    petViewModel.loadMediaForPet(petId)
    val pet = petViewModel.petsList.collectAsState().value.find { it.petId == petId } ?: Pet()
    var media =
        petViewModel.mediaList.collectAsState().value.find { it.petId == petId && it.type == "thumbnail" }


    var name by remember { mutableStateOf(if (edit) pet.name else "") }
    var species by remember { mutableStateOf(if (edit) pet.species else "") }
    var breed by remember { mutableStateOf(if (edit) pet.breed else "") }
    var birthDate by remember { mutableStateOf(if (edit) pet.birthDate else "") }
    var diet by remember { mutableStateOf(if (edit) pet.medicalInfo.diet else "") }
    var weight by remember { mutableStateOf(if (edit) pet.medicalInfo.weight else "") }
    var thumbnail by remember { mutableStateOf(if (edit) media?.url ?: "" else "") }
    var rotationAngle by remember { mutableStateOf(0f) }

    // Initialize the image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { result: List<Uri>? ->
        result?.let {
            if (it.isNotEmpty()) {
                Log.d("Image Selected", "Selected Images: ${it.joinToString()}")
                // Save the first selected image and update thumbnail
                thumbnail = ImageUtils.saveImageToInternalStorage(currentContext, it[0]) ?: ""
                Log.d("Image Selected", "Saved Image: $thumbnail")
            } else {
                Log.d("Image Selected", "No image selected.")
            }
        }
    }

    LaunchedEffect(media) {
        thumbnail = media?.url ?: "" // Update thumbnail when media changes
    }

    // Handle allergy changes locally
    var allergyInput by remember { mutableStateOf("") }
    val allergies = remember { mutableStateListOf(*pet.medicalInfo.allergies.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg))
    ) {
        TopAppBar(navController = navController, petName = if (edit) pet.name else "Add Pet",
            onSave = {
                // Create the updated pet object
                val updatedPet = pet.copy(
                    name = name,
                    species = species,
                    breed = breed,
                    birthDate = birthDate,
                    medicalInfo = pet.medicalInfo.copy(
                        allergies = allergies,
                        diet = diet,
                        weight = weight
                    )
                )
                val updatedMedia = Media(
                    petId = pet.petId,
                    type = "thumbnail",
                    url = thumbnail
                )

                // Call the update method in the ViewModel to save the pet
                if (edit) {
                    petViewModel.updateMedia(updatedMedia)
                    petViewModel.updatePet(updatedPet)
                } else {
                    petViewModel.addPetWithMedia(updatedPet, updatedMedia)
                }
                petViewModel.loadPets()
                petViewModel.getThumbnails()
                navController.popBackStack()
            })

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                CardMainScreenPreview(
                    media = thumbnail,
                    name = name,
                    breed = breed
                )
            }

            item {
                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.prim))
                ) {
                    Text("Change Image", color = Color.White)
                }
            }

            item {
                OutTextF(value = name, onValueChange = { name = it }, label = "Name")
            }
            item {
                OutTextF(value = species, onValueChange = { species = it }, label = "Species")
            }
            item {
                OutTextF(value = breed, onValueChange = { breed = it }, label = "Breed")
            }
            item {
                BirthDatePicker(
                    label = "Birth Date",
                    selectedDate = birthDate,
                    onDateSelected = { newDate ->
                        birthDate = newDate // Update the state with the selected date
                    }
                )
            }
            item {
                AllergyManager(
                    petViewModel = petViewModel,
                    petId = petId,
                    allergyInput = allergyInput,
                    setAllergyInput = { allergyInput = it },
                    allergies = allergies
                )
            }
            item {
                OutTextF(value = diet, onValueChange = { diet = it }, label = "Diet")
            }
            item {
                OutTextF(value = weight, onValueChange = { weight = it }, label = "Weight")
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Create the updated pet object
                        val updatedPet = pet.copy(
                            name = name,
                            species = species,
                            breed = breed,
                            birthDate = birthDate,
                            medicalInfo = pet.medicalInfo.copy(
                                allergies = allergies,
                                diet = diet,
                                weight = weight
                            )
                        )
                        val updatedMedia = Media(
                            petId = pet.petId,
                            type = "thumbnail",
                            url = thumbnail
                        )

                        // Call the update method in the ViewModel to save the pet
                        if (edit) {
                            petViewModel.updateMedia(updatedMedia)
                            petViewModel.updatePet(updatedPet)
                        } else {
                            petViewModel.addPetWithMedia(updatedPet, updatedMedia)
                        }
                        petViewModel.loadPets()
                        petViewModel.getThumbnails()
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.prim))
                ) {
                    Text("Save", color = Color.White)
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

}

    @Composable
    fun BirthDatePicker(
        label: String,
        selectedDate: String,
        onDateSelected: (String) -> Unit
    ) {
        var showDatePicker by remember { mutableStateOf(false) }

        // Open the Material Date Picker
        if (showDatePicker) {
            val datePicker = remember {
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            }

            datePicker.addOnPositiveButtonClickListener { timestamp ->
                val date = Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                onDateSelected(date.toString()) // Return formatted date
                showDatePicker = false
            }

            datePicker.addOnDismissListener {
                showDatePicker = false
            }

            // Show DatePicker
            datePicker.show(
                (LocalContext.current as AppCompatActivity).supportFragmentManager,
                "DatePicker"
            )
        }

        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true, // Make it read-only to ensure users interact via the picker
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        painter = painterResource(R.drawable.outline_calendar_month_32),
                        contentDescription = "Select Date",
                        tint = colorResource(R.color.prim)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.black_icon),
                unfocusedTextColor = colorResource(R.color.black_icon),
                focusedLabelColor = colorResource(R.color.black_icon),
                unfocusedLabelColor = colorResource(R.color.black_icon),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }


    @Composable
    fun CardMainScreenPreview(media: String, name: String, breed: String) {
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
                                        text = name,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    Text(
                                        text = breed,
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
                        ) {
                            val imageLoader = ImageLoader(LocalContext.current).newBuilder()
                                .allowHardware(true)
                                .crossfade(true)
                                .build()

                            val painter = rememberAsyncImagePainter(model = media, imageLoader = imageLoader)
                            val state = painter.state

                            val transition by animateFloatAsState(
                                targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
                                animationSpec = tween(durationMillis = 1000)
                            )
                            Image(
                                painter = painter,
                                contentDescription = "custom transition based on painter state",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scale(.8f + (.2f * transition))
                                    .graphicsLayer {
                                        rotationX = (1f - transition) * 5f
                                    }
                                    .alpha(min(1f, transition / .2f))
                            )
                            /*
                            AsyncImage(
                                model = media,
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


    @Composable
    fun AllergyManager(
        petViewModel: PetViewModel,
        petId: Int,
        allergyInput: String,
        setAllergyInput: (String) -> Unit,
        allergies: MutableList<String>
    ) {
        Column {
            // Input field for new allergy
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = allergyInput,
                    onValueChange = { setAllergyInput(it) },
                    label = { Text("Add an allergy") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.black_icon),
                        unfocusedTextColor = colorResource(R.color.black_icon),
                        focusedLabelColor = colorResource(R.color.black_icon),
                        unfocusedLabelColor = colorResource(R.color.black_icon),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = allergyInput.isNotBlank(),
                            enter = scaleIn(
                                animationSpec = keyframes {
                                    durationMillis = 500
                                    0.0f at 0 // Start at 0% scale
                                    1.2f at 300 // Overshoot to 120% scale
                                    1.0f at 500 // Settle at 100% scale
                                }
                            ),
                            exit = fadeOut(animationSpec = tween(durationMillis = 300)) // Simple fade-out
                        ) {
                            IconButton(
                                onClick = {
                                    if (allergyInput.isNotBlank()) {
                                        allergies.add(allergyInput.trim()) // Add allergy to the list
                                        setAllergyInput("") // Clear input field
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            color = colorResource(R.color.prim),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_add_32),
                                        contentDescription = "Add allergy",
                                        tint = colorResource(R.color.bg),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }


                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of allergies
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                allergies.forEach { allergy ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = allergy,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                allergies.remove(allergy) // Remove allergy from the list
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_remove_24),
                                contentDescription = "Remove allergy",
                                tint = colorResource(R.color.g_red),
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun OutTextF(value: String, onValueChange: (String) -> Unit, label: String) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
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
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.prim),
            )
        )
    }

    @Composable
    fun TopAppBar(navController: NavController, petName: String = "Edit Pet",
                  onSave: () -> Unit = {}) {
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
                        text = petName,
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
                onClick = onSave
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_check_32),
                        contentDescription = null,
                        tint = colorResource(id = R.color.prim),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }