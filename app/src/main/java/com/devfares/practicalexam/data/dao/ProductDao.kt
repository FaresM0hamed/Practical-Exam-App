package com.devfares.practicalexam.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devfares.practicalexam.data.model.ProductModel
import com.devfares.practicalexam.data.model.User

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductModel>)

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductModel>

    @Query("SELECT * FROM products WHERE title LIKE :query")
    suspend fun searchProducts(query: String): List<ProductModel>
}