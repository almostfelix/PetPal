package com.petpal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petpal.db.Pet
import com.petpal.db.PetViewModel

@Composable
fun AddNewPetScreen(navController: NavController, petViewModel: PetViewModel) {
    // Use mutableStateOf for state management
    val petName = remember { mutableStateOf("") }
    val petSpecies = remember { mutableStateOf("") }
    val petBreed = remember { mutableStateOf("") }
    val petBirthDate = remember { mutableStateOf("") }
    val petAllergies = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    // Function to handle the "Add Pet" button click
    fun addPet() {
        if (petName.value.isNotEmpty() && petSpecies.value.isNotEmpty() && petBreed.value.isNotEmpty() && petBirthDate.value.isNotEmpty()) {
            // Create the new Pet object
            val newPet = Pet(
                name = petName.value,
                species = petSpecies.value,
                breed = petBreed.value,
                birthDate = petBirthDate.value,
                allergies = petAllergies.value.split(",").map { it.trim() } // Split the allergies by commas and trim spaces
            )

            // Add the new pet to Firebase (view model handles Firebase logic)
            petViewModel.addPet(newPet)

            // Navigate back to the previous screen
            navController.popBackStack()
        } else {
            errorMessage.value = "All fields are required!" // Show error message if fields are empty
        }
    }

    // UI for the Add New Pet screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add New Pet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Pet Name Field
        OutlinedTextField(
            value = petName.value,
            onValueChange = { petName.value = it },
            label = { Text("Pet Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Species Field
        OutlinedTextField(
            value = petSpecies.value,
            onValueChange = { petSpecies.value = it },
            label = { Text("Species") },
            modifier = Modifier.fillMaxWidth()
        )

        // Breed Field
        OutlinedTextField(
            value = petBreed.value,
            onValueChange = { petBreed.value = it },
            label = { Text("Breed") },
            modifier = Modifier.fillMaxWidth()
        )

        // Birth Date Field
        OutlinedTextField(
            value = petBirthDate.value,
            onValueChange = { petBirthDate.value = it },
            label = { Text("Birth Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Allergies Field (optional)
        OutlinedTextField(
            value = petAllergies.value,
            onValueChange = { petAllergies.value = it },
            label = { Text("Allergies (comma separated)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Error Message (if any)
        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = androidx.compose.ui.graphics.Color.Red,
                fontSize = 14.sp
            )
        }

        // Add Pet Button
        Button(
            onClick = { addPet() },
            modifier = Modifier.fillMaxWidth(),
            enabled = petName.value.isNotEmpty() && petSpecies.value.isNotEmpty() && petBreed.value.isNotEmpty() && petBirthDate.value.isNotEmpty()
        ) {
            Text(text = "Add Pet")
        }
    }
}

