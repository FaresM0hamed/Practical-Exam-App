package com.devfares.practicalexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.devfares.practicalexam.adapters.ProductsAdapter
import com.devfares.practicalexam.data.dao.ProductDao
import com.devfares.practicalexam.data.local.LocalDatabase
import com.devfares.practicalexam.data.model.ProductModel
import com.devfares.practicalexam.data.network.ProductApis
import com.devfares.practicalexam.data.network.RetrofitClient
import com.devfares.practicalexam.data.repository.ProductRepository
import com.devfares.practicalexam.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var productDao: ProductDao
    private lateinit var adapter: ProductsAdapter
    private var products: List<ProductModel> = emptyList()
    private var filteredProducts: List<ProductModel> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateAdapter()
        setUpSearchView()

        productDao = LocalDatabase.getDatabase(requireContext()).productDao()
        val productApi = RetrofitClient.retrofit.create(ProductApis::class.java)
        val productRepository = ProductRepository(productDao, productApi)

        lifecycleScope.launch {
            products = productRepository.getProducts(requireContext())
            filteredProducts = products
            adapter.updateData(products)
        }
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText)
                return true
            }
        })
    }

    private fun filterData(query: String?) {
        if (!query.isNullOrBlank()) {
            lifecycleScope.launch {
                filteredProducts = productDao.searchProducts("%$query%")
                adapter.updateData(filteredProducts)
            }
        } else {
            filteredProducts = emptyList() // Reset the filtered list
            adapter.updateData(products)
        }
    }

    private fun initiateAdapter() {
        adapter = ProductsAdapter(emptyList())
        binding.productRecyclerView.adapter = adapter
    }
}