package com.tomtom.dentalfirst.data.model

data class ProductsJsonResponse(
    val catalog: List<Category> = emptyList(),
    val products: List<JsonProduct> = emptyList()
)

data class Category(
    val id: Long,
    val name: String,
    val image: String,
    val description: String,
    val items: List<SubCategory> = emptyList()
)

data class SubCategory(
    val id: Long,
    val name: String,
    val image: String,
    val description: String,
    val items: List<JsonProduct> = emptyList()
)

data class JsonProduct(
    val id: Long,
    val name: String,
    val image: String,
    val price: Long,
    val description: String
)

