package com.tomtom.dentalfirst.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomtom.dentalfirst.data.repository.ProductRepository
import com.tomtom.dentalfirst.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CatalogUiState {
    object Loading : CatalogUiState()
    data class Success(val products: List<Product>) : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
}

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepository.getInstance(application.applicationContext)
    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState

    init {
        observeProducts()
        loadProducts()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            repository.products.collect { products ->
                _uiState.value = if (products.isEmpty()) {
                    CatalogUiState.Error("Товары не найдены")
                } else {
                    CatalogUiState.Success(products)
                }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            repository.loadProducts()
        }
    }

    fun retry() {
        loadProducts()
    }
}

