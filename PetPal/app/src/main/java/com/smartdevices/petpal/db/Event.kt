package com.smartdevices.petpal.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class Event(
    @PrimaryKey(autoGenerate = false)
    val eventId: Int = -1,
    val petId: Int = -1,
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val type: String = ""
)