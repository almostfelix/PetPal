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

    // Function to load all pets (suspending function)
    fun loadPets() {
        viewModelScope.launch {
            _petsList.value = roomDB.getPets()  // Fetch pets from RoomDB
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
}

