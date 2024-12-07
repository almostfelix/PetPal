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
    val medicalInfo: MedicalInfo = MedicalInfo()
)

data class MedicalInfo(
    val allergies: List<String> = listOf(""), // e.g., "None" or "Peanuts"
    val diet: String = "", // e.g., "Grain-free"
    val weight: Double = 0.0, // Current weight in kilograms
)
data class MedicalEvent(
    val id: String = "", // Unique ID for the medical event
    val vetName: String = "", // Name of the veterinarian
    val clinicName: String = "", // Name of the clinic
    val date: String = "", // Date of the event
    val time: String = "", // Time of the event
    val type: String = "", // Type of event (e.g., vaccination, surgery)
    val description: String = "", // Detailed description of the event
    val medication: String = "", // Medication prescribed (if any)
    val notes: String = "" // Additional notes
)