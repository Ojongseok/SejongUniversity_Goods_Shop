package com.example.sejonggoodsmallproject.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.util.RetrofitInstance
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.databinding.ActivityMainBinding
import com.example.sejonggoodsmallproject.ui.view.favorite.FavoriteFragment
import com.example.sejonggoodsmallproject.ui.view.home.HomeFragment
import com.example.sejonggoodsmallproject.ui.view.mypage.MypageFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainRepository = MainRepository()
    private val factory = MainViewModelFactory(mainRepository)
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.fragment_home
        }

        viewModel = ViewModelProvider(this,factory) [MainViewModel::class.java]
//        viewModel.getTestData()
    }

    private fun retrofitWork() {
        val service = RetrofitInstance.retrofitService
        Log.d("태그","1")

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("태그","2")
            val response = service.getTestData()
            Log.d("태그","3")

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("태그",response.code().toString())
                } else {
                    Log.d("태그",response.code().toString())
                }
            }
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commit()
                    true
                }
                R.id.fragment_store -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, FavoriteFragment()).commit()
                    true
                }
                R.id.fragment_mypage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MypageFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}
