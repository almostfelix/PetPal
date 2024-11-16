package com.petpal.db

import android.content.Context

class RoomDB(context: Context) {

    private val appDatabase = DatabaseProvider.getDatabase(context)

    // Add a pet to the local database
    suspend fun addPet(pet: Pet) {
        appDatabase.petDao().addPet(pet)
    }

    // Get a specific pet by id from the local database
    suspend fun getPet(id: Int): Pet? {
        return appDatabase.petDao().getPet(id)
    }

    // Get all pets from the local database
    suspend fun getPets(): List<Pet> {
        return appDatabase.petDao().getPets()
    }

    // Get the last pet based on the highest ID (for generating the next ID)
    suspend fun getLastPet(): Pet? {
        return appDatabase.petDao().getLastPet()
    }

    // Delete a pet from the local database
    suspend fun deletePet(id: Int) {
        val pet = getPet(id)
        pet?.let {
            appDatabase.petDao().deletePet(it)
        }
    }
}
