package com.petpal.db

data class Pet(
    val name: String = "",
    val species: String = "",
    val breed: String = "",
    val birthDate: String = "",
    val imageUrl: String? = null,
    val allergies: List<String>? = null,
    val medicalRecords: List<MedicalRecord> = emptyList()
)