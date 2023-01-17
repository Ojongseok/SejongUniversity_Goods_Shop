package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {
    fun getTestData() = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.getTestData()
    }
}