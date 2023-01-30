package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {
    val result : MutableLiveData<List<ProductListData>> = MutableLiveData()

    suspend fun getTestData() = mainRepository.getTestData()
}