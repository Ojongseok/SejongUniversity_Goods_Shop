package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {

    suspend fun getTestData() = mainRepository.getTestData()

    fun getRecentSearchItemsList() : LiveData<List<RecentSearchModel>> {
        return mainRepository.getRecentSearchItemsList()
    }

    fun insertRecentSearch(recentSearchModel: RecentSearchModel) {
        mainRepository.insertRecentSearched(recentSearchModel)
    }

    fun deleteRecentSearch(recentSearchModel: RecentSearchModel) {
        mainRepository.deleteRecentSearched(recentSearchModel)
    }
}