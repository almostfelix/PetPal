package com.smartdevices.petpal.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media")
data class Media(
    @PrimaryKey(autoGenerate = false)
    val mediaId: Int = 0,
    val petId: Int = 0,
    val type: String = "",
    var url: String = ""
)