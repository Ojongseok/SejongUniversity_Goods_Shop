package com.example.sejonggoodsmallproject.ui.view.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.model.imgProductDetailInfoResult
import com.example.sejonggoodsmallproject.data.model.imgProductDetailResult
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.databinding.ActivityProductDetailBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.buy.BuyFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductViewModelViewModelFactory
import com.example.sejonggoodsmallproject.util.MyApplication
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: ProductDetailViewModel
    private lateinit var productImageViewPagerAdapter: ProductImageViewPagerAdapter
    private var itemId = 0
    lateinit var response : Response<ProductDetailResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemId = intent.getStringExtra("itemId")?.toInt()!!

        val mainRepository = MainRepository(application)
        val factory = ProductViewModelViewModelFactory(mainRepository, itemId)
        viewModel = ViewModelProvider(this,factory) [ProductDetailViewModel::class.java]

        binding.activity = this@ProductDetailActivity

        CoroutineScope(Dispatchers.IO).async {
            response = viewModel.getProductDetail()

            if (response.isSuccessful) {
                val data = response.body()
                binding.model = data

                withContext(Dispatchers.Main) {
                    setSomenailViewPager(data?.img!!)
                    setTabLayout(response.body()?.detailImg!!)

                    binding.tvProductDetailPrice.text = if (response.body()?.price in 1000..999999) {
                        val priceList = response.body()?.price.toString().toCharArray().toMutableList()
                        priceList.add(priceList.size-3,',')
                        priceList.joinToString("") + "원"
                    } else {
                        response.body()?.price.toString() + "원"
                    }
                }
            }
        }


    }
    private fun setTabLayout(detailImg: List<imgProductDetailInfoResult>) {
        // 초기 탭 세팅
        val bundle = Bundle()
        bundle.putSerializable("imgList",detailImg.toTypedArray())
        val productInfoFragment = ProductInfoFragment()
        productInfoFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,productInfoFragment).commit()

        binding.productDetailTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // tab이 선택되었을 때
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,productInfoFragment).commit()
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

    private fun setSomenailViewPager(imgList: List<imgProductDetailResult>) {
        productImageViewPagerAdapter = ProductImageViewPagerAdapter(applicationContext, imgList)

        binding.vpProductSomenail.apply {
            adapter = productImageViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tvIndicatorNowPosition.text = (position+1).toString()
                }
            })
        }

        binding.textView.text = imgList.size.toString()
    }

    fun buyButtonClick() {
        if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
            Toast.makeText(applicationContext,"로그인 후 이용 가능합니다.",Toast.LENGTH_SHORT).show()
        } else {
            val colorList = response.body()?.color
            val sizeList = response.body()?.size
            val price = response.body()?.price

            val bundle = Bundle().apply {
                putString("colorList",colorList)
                putString("sizeList",sizeList)
                putString("price",price.toString())
                putString("itemId", itemId.toString())
            }

            val buyFragment = BuyFragment()
            buyFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.vertical_from_bottom,0)
                .replace(R.id.lt_product_detail_buy, buyFragment).commit()
        }
    }

    fun backButtonClick() {
        finish()
    }
}