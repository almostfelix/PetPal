package com.smartdevices.petpal.db

data class MedicalRecord(
    val date: String = "",
    val vetName: String = "",
    val visitReason: String = "",
    val notes: String? = null
)