package com.petpal.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet")
data class Pet(
    @PrimaryKey(autoGenerate = false) // Set autoGenerate to false since we are managing ID generation ourselves
    var id: Int = 0,
    val name: String = "",
    val species: String = "",
    val breed: String = "",
    val birthDate: String = "",
    val imageUrl: String? = null,
    val allergies: List<String>? = null,
    val medicalRecords: List<MedicalRecord> = emptyList()
)