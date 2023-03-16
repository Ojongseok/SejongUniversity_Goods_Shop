package com.sejong.sejonggoodsmallproject.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sejong.sejonggoodsmallproject.data.repository.MainRepository
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}