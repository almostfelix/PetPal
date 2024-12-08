package com.smartdevices.petpal.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface PetDao {

    @Insert
    suspend fun addPet(pet: Pet)

    @Query("SELECT * FROM pet WHERE petId = :id LIMIT 1")
    suspend fun getPet(id: Int): Pet?

    @Query("SELECT * FROM pet")
    suspend fun getPets(): List<Pet>

    @Query("SELECT * FROM pet ORDER BY petId DESC LIMIT 1")
    suspend fun getLastPet(): Pet?  // Get the last inserted pet (highest ID)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Query("SELECT * FROM event WHERE petId = :petId")
    suspend fun getEventsForPet(petId: Int): List<Event>

    @Query("SELECT * FROM event ORDER BY eventId DESC LIMIT 1")
    suspend fun getLastEvent(): Event?  // Get the last inserted event (highest ID)

    @Query("SELECT * FROM media WHERE petId = :petId")
    suspend fun getMediaForPet(petId: Int): List<Media>

    @Query("SELECT * FROM media ORDER BY mediaId DESC LIMIT 1")
    suspend fun getLastMedia(): Media?  // Get the last inserted media (highest ID)

    @Query("SELECT * FROM media WHERE type = 'thumbnail'")
    suspend fun getThumbnails(): List<Media>

    @Upsert
    suspend fun addEvent(event: Event)

    @Upsert
    suspend fun addMedia(media: Media)

    @Update
    suspend fun updateMedia(media: Media)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Delete
    suspend fun deleteMedia(media: Media)
}
