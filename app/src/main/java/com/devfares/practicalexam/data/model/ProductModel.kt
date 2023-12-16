package com.devfares.practicalexam.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String
)