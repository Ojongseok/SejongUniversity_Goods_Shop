package com.example.sejonggoodsmallproject.ui.view.home.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.databinding.ActivityProductDetailBinding
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var productImageViewPagerAdapter: ProductImageViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this@ProductDetailActivity

        setSomenailViewPager()
        setTabLayout()

        binding.btnProductBuy.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.lt_product_detail_buy,BuyFragment()).commit()
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
        val item = ProductListData("이름1","새내기1",15000,
            listOf(
                "https://w.namu.la/s/cea148b61275282c1c8fcf35fb00d2143d6dea01924181cdaeffcc1682fc61bfb48ef63aecdba0dd4367b2d2c8e8b78e8bf115e4e4bdc76595d363acc53ba844cc152299a5a82ecf7a0339df665dc763e3c06d3a9a4c245fa885833b2ec4aebf",
                "https://mblogthumb-phinf.pstatic.net/20160817_259/retspe_14714118890125sC2j_PNG/%C7%C7%C4%AB%C3%F2_%281%29.png?type=w800",
                "https://post-phinf.pstatic.net/MjAxODA4MjhfMTUz/MDAxNTM1NDYyNjAzNTgz.qxcQtl6Y-M7GONFDFnl-VM4LIYrbKLs45svRYhtH2S8g.bRyxN5c1qcOP-_OvmkPOtKnOpD_ajftcddQqwQGw5AQg.PNG/pikachu-02.png?type=w1200"))

        productImageViewPagerAdapter = ProductImageViewPagerAdapter(applicationContext,item.imgList)

        binding.vpProductSomenail.apply {
            adapter = productImageViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tvIndicatorNowPosition.text = (position+1).toString()
                }
            })
        }
        binding.textView.text = item.imgList.size.toString()

    }
    fun backButtonClick() {
        finish()
    }
}