package com.smartdevices.petpal.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet")
data class Pet(
    @PrimaryKey(autoGenerate = false) // Set autoGenerate to false since we are managing ID generation ourselves
    var petId: Int = 0,
    val name: String = "",
    val species: String = "",
    val breed: String = "",
    val birthDate: String = "",
    val medicalInfo: MedicalInfo = MedicalInfo(),
)

data class MedicalInfo(
    val allergies: List<String> = listOf(), // e.g., "None" or "Peanuts"
    val diet: String = "", // e.g., "Grain-free"
    val weight: String = "", // Current weight in kilograms
)