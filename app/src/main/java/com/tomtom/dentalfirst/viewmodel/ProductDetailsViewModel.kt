package com.tomtom.dentalfirst.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomtom.dentalfirst.data.repository.ProductRepository
import com.tomtom.dentalfirst.data.model.Product
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProductDetailsViewModel(
    application: Application, private val productId: String
) : AndroidViewModel(application) {

    private val repository = ProductRepository.getInstance(application.applicationContext)

    val product: StateFlow<Product?> = repository.products.map { list ->
        list.find { it.id == productId }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )
}

