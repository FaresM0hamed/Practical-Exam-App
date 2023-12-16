package com.devfares.practicalexam.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val email: String,
    val username: String,
    var password: String
)