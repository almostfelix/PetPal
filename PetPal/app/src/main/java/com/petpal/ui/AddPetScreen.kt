package com.petpal.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.RenderEffect.createBlurEffect
import android.graphics.Shader
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.petpal.R
import com.petpal.db.Pet
import com.petpal.db.PetViewModel
import com.petpal.tools.ImagePickerLauncher
import com.petpal.tools.ImageUtils
import java.util.Calendar


@Preview
@Composable
fun AddNewPetScreenPreview() {
    AddNewPetScreen(
        rememberNavController(), PetViewModel(
            context = LocalContext.current
        )
    )
}


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPetScreen(navController: NavController, petViewModel: PetViewModel) {
    val petName = remember { mutableStateOf("") }
    val petSpecies = remember { mutableStateOf("") }
    val petBreed = remember { mutableStateOf("") }
    val petBirthDate = remember { mutableStateOf("") }
    val petAllergies = remember { mutableStateOf("") }
    val thumnnail = remember { mutableStateOf("") }

    // Track validity of each field
    val isPetNameValid = remember { mutableStateOf(true) }
    val isPetSpeciesValid = remember { mutableStateOf(true) }
    val isPetBreedValid = remember { mutableStateOf(true) }
    val isPetBirthDateValid = remember { mutableStateOf(true) }

    val currentContext = LocalContext.current

    var selectedImages = remember { mutableStateOf(emptyList<Uri>()) }

    // Initialize the image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { result: List<Uri>? ->
        result?.let {
            if (it.isNotEmpty()) {
                selectedImages.value = it
                Log.d("Image Selected", "Selected Images: ${it.joinToString()}")
                thumnnail.value = ImageUtils.saveImageToInternalStorage(currentContext, it[0]) ?: ""
                Log.d("Image Selected", "Saved Image: $thumnnail")
            } else {
                Log.d("Image Selected", "No image selected.")
            }
        }
    }


    fun validateFields() {
        isPetNameValid.value = petName.value.isNotEmpty()
        isPetSpeciesValid.value = petSpecies.value.isNotEmpty()
        isPetBreedValid.value = petBreed.value.isNotEmpty()
        isPetBirthDateValid.value = petBirthDate.value.isNotEmpty()
    }


    fun addPet() {
        validateFields()
        if (isPetNameValid.value && isPetSpeciesValid.value && isPetBreedValid.value && isPetBirthDateValid.value) {
            val newPet = Pet(
                id = 1,
                name = petName.value,
                species = petSpecies.value,
                breed = petBreed.value,
                birthDate = petBirthDate.value,
                allergies = petAllergies.value.split(",").map { it.trim()},
                thumbnail = thumnnail.value
            )
            Log.d("AddNewPetScreen", "Adding new pet: $newPet")
            //preferenceManager.addPet(newPet)
            petViewModel.addNewPet(newPet)
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.color_bg),
                contentScale = ContentScale.FillHeight
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ){
            AsyncImage(
                model = thumnnail.value,
                contentDescription = "Selected Pet Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier.padding(8.dp).align(Alignment.TopStart),
            ){
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

            }

            Box(
                modifier = Modifier.padding(8.dp).align(Alignment.TopEnd),
            ){
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
                    onClick = { imagePickerLauncher.launch("image/*") }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_image_32),
                            contentDescription = null,
                            tint = colorResource(id = R.color.black_icon),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

            }


        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
        ){
            Card(
                modifier = Modifier
                    .padding(8.dp, 32.dp, 8.dp, 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    ) {
                        TextField(
                            value = petName.value,
                            onValueChange = {
                                petName.value = it
                                isPetNameValid.value = petName.value.isNotEmpty()
                            },
                            label = { Text(stringResource(R.string.pet_name)) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            isError = !isPetNameValid.value,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = colorResource(R.color.black_icon),
                                unfocusedLabelColor = colorResource(R.color.black_icon),
                                errorLabelColor = colorResource(R.color.error_red),
                                errorContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(25.dp),
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    ) {
                        TextField(
                            value = petSpecies.value,
                            onValueChange = {
                                petSpecies.value = it
                                isPetSpeciesValid.value = petSpecies.value.isNotEmpty()
                            },
                            label = { Text(stringResource(R.string.species)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !isPetSpeciesValid.value,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = colorResource(R.color.black_icon),
                                unfocusedLabelColor = colorResource(R.color.black_icon),
                                errorLabelColor = colorResource(R.color.error_red),
                                errorContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(25.dp),
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    ) {
                        TextField(
                            value = petBreed.value,
                            onValueChange = {
                                petBreed.value = it
                                isPetBreedValid.value = petBreed.value.isNotEmpty()
                            },
                            label = { Text(stringResource(R.string.breed)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !isPetBreedValid.value,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = colorResource(R.color.black_icon),
                                unfocusedLabelColor = colorResource(R.color.black_icon),
                                errorLabelColor = colorResource(R.color.error_red),
                                errorContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(25.dp),
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    ) {
                        TextField(
                            value = petBirthDate.value,
                            onValueChange = {
                                petBirthDate.value = it
                                isPetBirthDateValid.value = petBirthDate.value.isNotEmpty()
                            },
                            label = { Text(text = stringResource(R.string.date_of_birth)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !isPetBirthDateValid.value,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = colorResource(R.color.black_icon),
                                unfocusedLabelColor = colorResource(R.color.black_icon),
                                errorLabelColor = colorResource(R.color.error_red),
                                errorContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(25.dp),
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        val calendar = Calendar.getInstance()
                                        val datePickerDialog = DatePickerDialog(
                                            currentContext,
                                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                                petBirthDate.value = "$dayOfMonth/${month + 1}/$year"
                                                isPetBirthDateValid.value = petBirthDate.value.isNotEmpty()
                                            },
                                            calendar.get(Calendar.YEAR),
                                            calendar.get(Calendar.MONTH),
                                            calendar.get(Calendar.DAY_OF_MONTH)
                                        )
                                        datePickerDialog.show()
                                    },
                                    modifier = Modifier
                                        .padding(0.dp, 0.dp, 8.dp, 0.dp)
                                        .background(
                                            color = colorResource(R.color.accent),
                                            shape = CircleShape
                                        ),
                                )
                                {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_calendar_month_32),
                                        contentDescription = "Pick date",
                                        tint = colorResource(R.color.black_icon)
                                    )
                                }
                            }
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    ) {
                        TextField(
                            value = petAllergies.value,
                            onValueChange = { petAllergies.value = it },
                            label = { Text(stringResource(R.string.allergies)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = colorResource(R.color.black_icon),
                                unfocusedLabelColor = colorResource(R.color.black_icon),
                                errorLabelColor = colorResource(R.color.error_red),
                                errorContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(25.dp),
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { addPet() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.accent_dark),
                    )
                ) {
                    Text(text = stringResource(R.string.add_pet_btn))
                }

            }
        }
    }
}