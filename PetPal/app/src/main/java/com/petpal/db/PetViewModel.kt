package com.petpal.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PetViewModel : ViewModel() {

    private val firebaseHelper = FirebaseHelper()

    val petsList = MutableLiveData<List<Pet>>()
    val errorMessage = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()

    // Add a new pet to the database
    fun addPet(pet: Pet) {
        firebaseHelper.addNewPet(pet, onSuccess = {
            successMessage.value = "Pet added successfully with ID: $it"
        }, onFailure = {
            errorMessage.value = it
        })
    }

    // Fetch all pets from Firebase
    fun fetchPets() {
        firebaseHelper.fetchAllPets(onSuccess = { pets ->
            petsList.value = pets
        }, onFailure = {
            errorMessage.value = it
        })
    }

    // Fetch a specific pet by ID
    fun fetchPetById(petId: String) {
        firebaseHelper.fetchPetById(petId, onSuccess = { pet ->
            petsList.value = listOf(pet) // Display the specific pet
        }, onFailure = {
            errorMessage.value = it
        })
    }

    // Add a medical record for a specific pet
    fun addMedicalRecord(petId: String, record: MedicalRecord) {
        firebaseHelper.addMedicalRecord(petId, record, onSuccess = {
            successMessage.value = "Medical record added successfully"
        }, onFailure = {
            errorMessage.value = it
        })
    }

    // Delete a pet from Firebase
    fun deletePet(petId: String) {
        firebaseHelper.deletePet(petId, onSuccess = {
            successMessage.value = "Pet deleted successfully"
        }, onFailure = {
            errorMessage.value = it
        })
    }
}
