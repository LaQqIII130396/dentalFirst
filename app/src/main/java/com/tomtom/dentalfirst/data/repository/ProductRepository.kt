package com.tomtom.dentalfirst.data.repository

import android.content.Context
import com.google.gson.Gson
import com.tomtom.dentalfirst.R
import com.tomtom.dentalfirst.data.model.ProductsJsonResponse
import com.tomtom.dentalfirst.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class ProductRepository private constructor(private val context: Context) {
    private val gson = Gson()
    private val productsDrawableRes = listOf(R.drawable.first, R.drawable.second, R.drawable.third)
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private var isLoaded = false

    suspend fun loadProducts() {
        if (isLoaded) return
        _products.value = parseJson()
        isLoaded = true
    }

    private suspend fun parseJson(): List<Product> = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.assets.open("products.json")
            val reader = InputStreamReader(inputStream)

            val response = gson.fromJson(reader, ProductsJsonResponse::class.java)

            reader.close()

            val loadedProducts = mutableListOf<Product>()

            response.catalog.forEach { category ->
                category.items.forEach { subCategory ->
                    subCategory.items.forEach { product ->
                        loadedProducts.add(
                            Product(
                                id = product.id.toString(),
                                name = product.name,
                                price = "₽${product.price}",
                                imageRes = productsDrawableRes.random(),
                                description = product.description
                            )
                        )
                    }
                }
            }

            response.products.forEach { product ->
                loadedProducts.add(
                    Product(
                        id = product.id.toString(),
                        name = product.name,
                        price = "₽${product.price}",
                        imageRes = productsDrawableRes.random(),
                        description = product.description
                    )
                )
            }
            loadedProducts
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    companion object {
        private var instance: ProductRepository? = null

        fun getInstance(context: Context): ProductRepository {
            if (instance == null) {
                instance = ProductRepository(context)
            }
            return instance!!
        }
    }
}
