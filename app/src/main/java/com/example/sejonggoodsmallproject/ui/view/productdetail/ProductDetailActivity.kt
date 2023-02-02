package com.example.sejonggoodsmallproject.ui.view.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductDetailData
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.databinding.ActivityProductDetailBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.buy.BuyFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModelFactory
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductViewModelViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var productImageViewPagerAdapter: ProductImageViewPagerAdapter
    private var itemId = 0
    lateinit var result : Response<ProductDetailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemId = intent.getStringExtra("itemId")?.toInt()!!

        val mainRepository = MainRepository(application)
        val factory = ProductViewModelViewModelFactory(mainRepository, itemId)
        viewModel = ViewModelProvider(this,factory) [ProductDetailViewModel::class.java]

        binding.activity = this@ProductDetailActivity

        setSomenailViewPager()
        setTabLayout()

        CoroutineScope(Dispatchers.IO).launch {
            result = viewModel.getProductDetail(itemId)

            if (result.isSuccessful) {
                val data = result.body()
                binding.model = data
            }
        }


    }
    private fun setTabLayout() {
        // 초기 탭 세팅
        supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,ProductInfoFragment()).commit()

        binding.productDetailTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // tab이 선택되었을 때
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,ProductInfoFragment()).commit()
                    1 -> supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,ProductReviewFragment()).commit()
                    2 -> supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,ProductQuestionFragment()).commit()
                }
            }
            // tab이 선택되지 않았을 때
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            // tab이 다시 선택되었을 때
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setSomenailViewPager() {
        productImageViewPagerAdapter = ProductImageViewPagerAdapter(applicationContext, emptyList())

        binding.vpProductSomenail.apply {
            adapter = productImageViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tvIndicatorNowPosition.text = (position+1).toString()
                }
            })
        }

//        binding.textView.text = item.imgList.size.toString()

    }
    fun buyButtonClick() {
        supportFragmentManager.beginTransaction().replace(R.id.lt_product_detail_buy, BuyFragment()).commit()
    }
    fun backButtonClick() {
        finish()
    }
}