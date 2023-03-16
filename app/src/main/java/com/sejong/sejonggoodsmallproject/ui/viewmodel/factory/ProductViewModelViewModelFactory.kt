package com.sejong.sejonggoodsmallproject.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sejong.sejonggoodsmallproject.data.repository.MainRepository
import com.sejong.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import java.lang.IllegalArgumentException

class ProductViewModelViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}