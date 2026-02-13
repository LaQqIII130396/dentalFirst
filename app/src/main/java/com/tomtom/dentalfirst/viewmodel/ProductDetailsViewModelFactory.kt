package com.tomtom.dentalfirst.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ProductDetailsViewModelFactory(private val productId: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            ?: throw IllegalStateException("Application key not found")
        return ProductDetailsViewModel(application, productId) as T
    }

    companion object {
        fun provideFactory(productId: String): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ProductDetailsViewModel(application!!, productId)
                }
            }
        }
    }
}

