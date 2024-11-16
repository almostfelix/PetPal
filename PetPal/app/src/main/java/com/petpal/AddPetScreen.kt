package com.petpal

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petpal.db.Pet
import com.petpal.db.PetViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPetScreen(navController: NavController, petViewModel: PetViewModel) {
    val petName = remember { mutableStateOf("") }
    val petSpecies = remember { mutableStateOf("") }
    val petBreed = remember { mutableStateOf("") }
    val petBirthDate = remember { mutableStateOf("") }
    val petAllergies = remember { mutableStateOf("") }

    // Track validity of each field
    val isPetNameValid = remember { mutableStateOf(true) }
    val isPetSpeciesValid = remember { mutableStateOf(true) }
    val isPetBreedValid = remember { mutableStateOf(true) }
    val isPetBirthDateValid = remember { mutableStateOf(true) }

    val currentContext = LocalContext.current

    fun validateFields(){
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
                allergies = petAllergies.value.split(",").map { it.trim() }
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
            .background(colorResource(R.color.bg))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = petName.value,
            onValueChange = {
                petName.value = it
                isPetNameValid.value = petName.value.isNotEmpty() },
            label = { Text(stringResource(R.string.pet_name)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPetNameValid.value,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.accent_dark),
                unfocusedBorderColor = colorResource(R.color.prim),
                focusedTextColor = colorResource(R.color.black_icon),
                unfocusedTextColor = colorResource(R.color.black_icon),
                errorTextColor = Color.Red,
            )
        )

        OutlinedTextField(
            value = petSpecies.value,
            onValueChange = {
                petSpecies.value = it
                isPetSpeciesValid.value = petSpecies.value.isNotEmpty() },
            label = { Text(stringResource(R.string.species)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPetSpeciesValid.value,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.accent_dark),
                unfocusedBorderColor = colorResource(R.color.prim),
                focusedTextColor = colorResource(R.color.black_icon),
                unfocusedTextColor = colorResource(R.color.black_icon),
                errorTextColor = Color.Red,
            )
        )

        OutlinedTextField(
            value = petBreed.value,
            onValueChange = { petBreed.value = it
                isPetBreedValid.value = petBreed.value.isNotEmpty()},
            label = { Text(stringResource(R.string.breed)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPetBreedValid.value,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.accent_dark),
                unfocusedBorderColor = colorResource(R.color.prim),
                focusedTextColor = colorResource(R.color.black_icon),
                unfocusedTextColor = colorResource(R.color.black_icon),
                errorTextColor = Color.Red,
            )
        )

        OutlinedTextField(
            value = petBirthDate.value,
            onValueChange = { petBirthDate.value = it
                isPetBirthDateValid.value = petBirthDate.value.isNotEmpty()},
            label = { Text(text = stringResource(R.string.date_of_birth)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPetBirthDateValid.value,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.accent_dark),
                unfocusedBorderColor = colorResource(R.color.prim),
                focusedTextColor = colorResource(R.color.black_icon),
                unfocusedTextColor = colorResource(R.color.black_icon),
                errorTextColor = Color.Red,
            ),
            trailingIcon = {
                IconButton(onClick = {
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
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_calendar_month_32),
                        contentDescription = "Pick date",
                        tint = colorResource(R.color.disabled)
                    )
                }
            }
        )

        OutlinedTextField(
            value = petAllergies.value,
            onValueChange = { petAllergies.value = it },
            label = { Text(stringResource(R.string.allergies)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.accent_dark),
                unfocusedBorderColor = colorResource(R.color.prim),
                focusedTextColor = colorResource(R.color.black_icon),
                unfocusedTextColor = colorResource(R.color.black_icon),
                errorTextColor = Color.Red,
            )
        )

        Button(
            onClick = { addPet() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.accent_dark),
            )
        ) {
            Text(text = stringResource(R.string.add_pet_btn))
        }
    }
}
