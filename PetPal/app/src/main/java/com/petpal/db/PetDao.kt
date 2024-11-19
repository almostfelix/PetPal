package com.petpal.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PetDao {

    @Insert
    suspend fun addPet(pet: Pet)

    @Query("SELECT * FROM pet WHERE id = :id LIMIT 1")
    suspend fun getPet(id: Int): Pet?

    @Query("SELECT * FROM pet")
    suspend fun getPets(): List<Pet>

    @Query("SELECT * FROM pet ORDER BY id DESC LIMIT 1")
    suspend fun getLastPet(): Pet?  // Get the last inserted pet (highest ID)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Query("UPDATE pet SET events = :events WHERE id = :id")
    suspend fun updatePetEvents(id: Int, events: List<Event>)
}
