package com.petpal.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PetViewModel(context: Context) : ViewModel() {

    private val roomDB = RoomDB(context)

    private val _petsList = MutableLiveData<List<Pet>>()
    val petsList: LiveData<List<Pet>> = _petsList

    private val _pet = MutableLiveData<Pet>()
    val pet: LiveData<Pet> = _pet

    private val _eventsList = MutableLiveData<List<Event>>()
    val eventsList: LiveData<List<Event>> = _eventsList

    // Function to load all pets (suspending function)
    fun loadPets() {
        viewModelScope.launch {
            _petsList.value = roomDB.getPets()  // Fetch pets from RoomDB
        }
    }

    fun getPet(id: Int){
        viewModelScope.launch {
            _pet.value = roomDB.getPet(id)
        }
    }

    fun addNewPet(pet: Pet) {
        viewModelScope.launch {
            roomDB.getLastPet()?.let {
                pet.id = it.id + 1
            }
            roomDB.addPet(pet)  // This call is now inside a coroutine
            loadPets()
        }
    }

    suspend fun getPetById(id: Int): Pet? {
        return roomDB.getPet(id)
    }

    suspend fun getAllPets(): List<Pet> {
        return roomDB.getPets()
    }

    suspend fun removePet(id: Int) {
        roomDB.deletePet(id)
    }


    fun loadEventsForPet(petId: Int) {
        viewModelScope.launch {
            _eventsList.value = roomDB.getEventsForPet(petId)
        }
    }

    fun addEventToPet(petId: Int, event: Event) {
        viewModelScope.launch {
            // Update the database
            val pet = roomDB.getPet(petId) // Fetch the pet from the database
            pet?.let {
                val updatedPet = it.copy(events = it.events + event) // Add the new event
                roomDB.updatePet(updatedPet) // Save the updated pet back to the database
            }

            // Reload the pets list to reflect changes in LiveData
            _petsList.value = roomDB.getPets()
        }
    }

    fun removeEventFromPet(petId: Int, event: Event) {
        viewModelScope.launch {
            roomDB.removeEventFromPet(petId, event)
            loadEventsForPet(petId)
        }
    }
}

