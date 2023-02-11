package com.example.sejonggoodsmallproject.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.databinding.ActivityMainBinding
import com.example.sejonggoodsmallproject.ui.view.cart.CartFragment
import com.example.sejonggoodsmallproject.ui.view.mypage.MypageFragment
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.view.search.SearchFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModelFactory
import com.example.sejonggoodsmallproject.util.MyApplication
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: MainViewModel
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var response : List<ProductListResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mainRepository = MainRepository(application)
        val factory = MainViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this,factory) [MainViewModel::class.java]


        setTabLayout()

        binding.btnSearch.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front,0)
                .replace(R.id.main_container, SearchFragment()).commit()
        }

        binding.btnCart.setOnClickListener {
            if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
                Toast.makeText(applicationContext,"로그인 후 이용 가능합니다.",Toast.LENGTH_SHORT).show()
            } else {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .replace(R.id.main_container, CartFragment()).commit()
            }
        }

        binding.btnMypage.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front,0)
                .replace(R.id.main_container, MypageFragment()).commit()
        }
    }

    private fun setRvProductList() {
        CoroutineScope(Dispatchers.IO).launch {
            response = viewModel.getAllProducts()
            productListAdapter = ProductListAdapter(applicationContext, response)

            withContext(Dispatchers.Main) {
                binding.rvProductList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = productListAdapter
                    addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(applicationContext).orientation))
                }

                productListAdapter.setItemClickListener(object : ProductListAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int) {
                        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
                        intent.putExtra("itemId", response[position].id.toString())
                        startActivity(intent)
                    }
                })
            }
        }
    }

//    fun onClick(view: View) {
//        when(view.id) {
//            R.id.btn_search -> {
//                supportFragmentManager.beginTransaction().replace(R.id.main_container, SearchFragment()).commit()
//            }
//            R.id.btn_cart -> {
//                supportFragmentManager.beginTransaction().replace(R.id.main_container, CartFragment()).commit()
//            }
//            R.id.btn_mypage -> {
//                supportFragmentManager.beginTransaction().replace(R.id.main_container, MypageFragment()).commit()
//            }
//        }
//    }

    private fun setTabLayout() {
        // 초기 tab 세팅
        setRvProductList()

        binding.storeFragmentTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // tab이 선택되었을 때
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    // 0, 1, 2... 탭 구분하기
                }
            }
            // tab이 선택되지 않았을 때
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            // tab이 다시 선택되었을 때
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    var mBackWait:Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}
