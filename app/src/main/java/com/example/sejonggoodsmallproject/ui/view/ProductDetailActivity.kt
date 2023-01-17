package com.example.sejonggoodsmallproject.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.databinding.ActivityMainBinding
import com.example.sejonggoodsmallproject.databinding.ActivityProductDetailBinding
import com.example.sejonggoodsmallproject.databinding.FragmentHomeBinding
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var productSomenailViewPagerAdapter: ProductSomenailViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        setSomenailViewPager()

        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }
    private fun setSomenailViewPager() {
        val item = ProductListData("이름1","새내기1",15000,
            listOf("https://w.namu.la/s/cea148b61275282c1c8fcf35fb00d2143d6dea01924181cdaeffcc1682fc61bfb48ef63aecdba0dd4367b2d2c8e8b78e8bf115e4e4bdc76595d363acc53ba844cc152299a5a82ecf7a0339df665dc763e3c06d3a9a4c245fa885833b2ec4aebf",
                "https://mblogthumb-phinf.pstatic.net/20160817_259/retspe_14714118890125sC2j_PNG/%C7%C7%C4%AB%C3%F2_%281%29.png?type=w800"))

        productSomenailViewPagerAdapter = ProductSomenailViewPagerAdapter(applicationContext,item.imgList)

        binding.vpProductSomenail.apply {
            adapter = productSomenailViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tvIndicatorNowPosition.text = (position+1).toString()
                }
            })
        }
        binding.textView.text = item.imgList.size.toString()

    }
}