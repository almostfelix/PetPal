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


    // Add event to a specific pet
    suspend fun addEventToPet(petId: Int, event: Event) {
        val pet = getPet(petId)
        pet?.let {
            // Convert existing events from JSON string to list of Event objects
            val updatedEvents = it.events.toMutableList().apply { add(event) }
            // Convert list back to JSON string
            val updatedPet = it.copy(events = updatedEvents)
            appDatabase.petDao().updatePet(updatedPet)
        }
    }

    suspend fun getEventsForPet(petId: Int): List<Event> {
        return getPet(petId)?.events ?: emptyList()
    }

    suspend fun removeEventFromPet(petId: Int, event: Event) {
        val pet = getPet(petId)
        pet?.let {
            val updatedEvents = it.events.filter { it != event }
            appDatabase.petDao().updatePetEvents(petId, updatedEvents)
        }
    }
}
