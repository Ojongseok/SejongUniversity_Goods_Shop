package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import java.lang.IllegalArgumentException

class ProductViewModelViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}