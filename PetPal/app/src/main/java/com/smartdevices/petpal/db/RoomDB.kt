package com.smartdevices.petpal.db

import android.content.Context

class RoomDB(context: Context) {

    private val appDatabase = DatabaseProvider.getDatabase(context)

    // Add a pet to the local database
    suspend fun addPet(pet: Pet) {
        appDatabase.petDao().addPet(pet)
    }

    suspend fun updatePet(pet: Pet) {
        appDatabase.petDao().updatePet(pet)
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
    suspend fun deletePet(pet: Pet) {
        appDatabase.petDao().deletePet(pet)
    }


    suspend fun addEvent(event: Event) {
        appDatabase.petDao().addEvent(event)
    }

    suspend fun getEventsForPet(petId: Int): List<Event> {
        return appDatabase.petDao().getEventsForPet(petId)
    }

    suspend fun getLastEvent(): Event? {
        return appDatabase.petDao().getLastEvent()
    }

    suspend fun deleteEvent(event: Event) {
        appDatabase.petDao().deleteEvent(event)
    }

    suspend fun deleteEventFromPet(petId: Int, eventId: Int) {
        appDatabase.petDao().deleteEventFromPet(petId = petId, eventId = eventId)
    }

    suspend fun getThumbnails(): List<Media> {
        return appDatabase.petDao().getThumbnails()
    }

    suspend fun addMedia(media: Media) {
        appDatabase.petDao().addMedia(media)
    }

    suspend fun updateMedia(media: Media) {
        appDatabase.petDao().updateMedia(media)
    }

    suspend fun getMediaForPet(petId: Int): List<Media> {
        return appDatabase.petDao().getMediaForPet(petId)
    }

    suspend fun getLastMedia(): Media? {
        return appDatabase.petDao().getLastMedia()
    }

    suspend fun deleteMedia(media: Media) {
        appDatabase.petDao().deleteMedia(media)
    }

    suspend fun deleteMediaForPet(petId: Int, mediaId: Int) {
        appDatabase.petDao().deleteMediaForPet(petId = petId, mediaId = mediaId)
    }
}
