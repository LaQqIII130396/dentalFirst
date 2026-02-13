package com.tomtom.dentalfirst.data.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val price: String,
    @param:DrawableRes val imageRes: Int,
    val description: String? = null
)
