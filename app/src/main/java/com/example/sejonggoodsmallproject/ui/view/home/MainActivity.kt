package com.example.sejonggoodsmallproject.ui.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.databinding.ActivityMainBinding
import com.example.sejonggoodsmallproject.ui.view.cart.CartFragment
import com.example.sejonggoodsmallproject.ui.view.login.InitActivity
import com.example.sejonggoodsmallproject.ui.view.mypage.MypageFragment
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.view.search.SearchFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.factory.MainViewModelFactory
import com.example.sejonggoodsmallproject.util.MyApplication
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_login_confirm.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: MainViewModel
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var response : List<ProductListResponse>
    private lateinit var result : List<ProductListResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mainRepository = MainRepository(application)
        val factory = MainViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this,factory) [MainViewModel::class.java]

        binding.activity = this

        setTabLayout()
    }

    private fun setLoginDialog() {
        val loginDialog = LoginDialog(this)

        loginDialog.showDialog()

        loginDialog.dialog.btn_dialog_login.setOnClickListener {
            startActivity(Intent(this, InitActivity::class.java))
            finish()
        }

        loginDialog.dialog.btn_dialog_login_close.setOnClickListener {
            loginDialog.dialog.dismiss()
        }
    }

    private fun setTabLayout() {
        setRvProductList()

        binding.storeFragmentTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateRecyclerView(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun setRvProductList() {
        CoroutineScope(Dispatchers.IO).launch {
            response = viewModel.getAllProducts()
            result = response

            productListAdapter = ProductListAdapter(applicationContext, response)

            withContext(Dispatchers.Main) {
                binding.rvProductList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = productListAdapter
                    addItemDecoration(DividerItemDecoration(applicationContext,LinearLayoutManager(applicationContext).orientation))
                }

                productListAdapter.setItemClickListener(object :
                    ProductListAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int) {
                        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
                        intent.putExtra("itemId", result[position].id.toString())
                        startActivity(intent)
                    }
                })
            }
        }
    }

    private fun updateRecyclerView(tabId : Int) {
        if (tabId == 0) {
            result = response
            productListAdapter.setData(result)
        } else {
            productListAdapter.apply {
                result = response.filter {
                    it.categoryId.toInt() == tabId
                }
                setData(result)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_search -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .add(R.id.main_container, SearchFragment(),"backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()
            }

            R.id.btn_cart -> {
                if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
                    setLoginDialog()
                } else {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.horizon_enter_front,0)
                        .add(R.id.main_container, CartFragment(), "backStack")
                        .addToBackStack("backStack")
                        .commitAllowingStateLoss()
                }
            }

            R.id.btn_mypage -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .add(R.id.main_container, MypageFragment(), "backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()
            }
        }
    }

    var mBackWait:Long = 0
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            if(System.currentTimeMillis() - mBackWait >=2000 ) {
                mBackWait = System.currentTimeMillis()
                Toast.makeText(applicationContext,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }
}
