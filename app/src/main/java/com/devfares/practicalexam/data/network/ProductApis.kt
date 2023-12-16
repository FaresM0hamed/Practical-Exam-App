package com.devfares.practicalexam.data.network

import com.devfares.practicalexam.data.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ProductApis {
    @GET("/products")
    suspend fun getProducts(): Response<List<ProductModel>>
}