package com.devfares.practicalexam.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.devfares.practicalexam.data.dao.ProductDao
import com.devfares.practicalexam.data.model.ProductModel
import com.devfares.practicalexam.data.network.ProductApis

class ProductRepository(private val productDao: ProductDao, private val productApis: ProductApis) {

    suspend fun getProducts(context: Context): List<ProductModel> {
        return try {
            val localData = productDao.getProducts()
            if (isInternetConnected(context)) {
                val remoteData = productApis.getProducts().body() ?: emptyList()
                productDao.insertProducts(remoteData)
                remoteData
            } else localData
        } catch (e: Exception) {
            Log.e("ProductRepository","${e.message}")
            emptyList()
        }
    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
