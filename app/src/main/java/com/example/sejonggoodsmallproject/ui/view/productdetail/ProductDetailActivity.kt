package com.example.sejonggoodsmallproject.ui.view.productdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.model.imgProductDetailInfoResult
import com.example.sejonggoodsmallproject.data.model.imgProductDetailResult
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.databinding.ActivityProductDetailBinding
import com.example.sejonggoodsmallproject.ui.view.home.LoginDialog
import com.example.sejonggoodsmallproject.ui.view.login.InitActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.buy.BuyFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.factory.ProductViewModelViewModelFactory
import com.example.sejonggoodsmallproject.util.MyApplication
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_login_confirm.*
import kotlinx.android.synthetic.main.dialog_product_detail_help.*
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
    private var isScraped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this@ProductDetailActivity
        itemId = intent.getStringExtra("itemId")?.toInt()!!

        val mainRepository = MainRepository(application)
        val factory = ProductViewModelViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this,factory) [ProductDetailViewModel::class.java]

        CoroutineScope(Dispatchers.IO).async {
            response = viewModel.getProductDetail(itemId)

            if (response.isSuccessful) {
                val data = response.body()!!
                binding.model = data

                Log.d("tag",data.scraped.toString())

                withContext(Dispatchers.Main) {
                    setSomenailViewPager(data.img)
                    setTabLayout(data.detailImg)

                    binding.tvProductDetailPrice.text = if (data.price in 1000..999999) {
                        val priceList = data.price.toString().toCharArray().toMutableList()
                        priceList.add(priceList.size-3,',')
                        priceList.joinToString("") + "원"
                    } else {
                        data.price.toString() + "원"
                    }

                    if (data.scraped) {
                        isScraped = true
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_on)
                    }else {
                        isScraped = false
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_off)
                    }
                }
            }
        }

    }
    private fun setTabLayout(detailImg: List<imgProductDetailInfoResult>) {
        val bundle = Bundle()
        bundle.putSerializable("imgList",detailImg.toTypedArray())
        val productInfoFragment = ProductInfoFragment()
        productInfoFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.product_detail_info_container,productInfoFragment).commit()

        binding.productDetailTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.product_detail_info_container,productInfoFragment)
                            .commit()
                    }

                    1 -> {
                        val bundle = Bundle()
                        bundle.putSerializable("sellerInfo", response.body()?.seller)
                        val productSellerFragment = ProductSellerFragment()
                        productSellerFragment.arguments = bundle

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.product_detail_info_container, productSellerFragment)
                            .commit()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
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

    private fun setLoginDialog() {
        val loginDialog = LoginDialog(this)

        loginDialog.showDialog()

        loginDialog.dialog.btn_dialog_login.setOnClickListener {
            val intent = Intent(this, InitActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        loginDialog.dialog.btn_dialog_login_close.setOnClickListener {
            loginDialog.dialog.dismiss()
        }
    }

    private fun setPdHelp() {
        val pdHelpDialog = HelpDialog(this)

        pdHelpDialog.showDialog()

        pdHelpDialog.dialog.btn_dialog_pd_help_complete.setOnClickListener {
            pdHelpDialog.dialog.dismiss()
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back_button -> {
                finish()
            }

            R.id.btn_favorite -> {
                if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
                    setLoginDialog()
                } else {
                    if (isScraped) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = viewModel.deleteFavorite(itemId.toLong())
                            Log.d("tag", result.body()?.toString()!!)
                        }
                        isScraped = false
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_off)
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = viewModel.addFavorite(itemId.toLong())
                            Log.d("tag", result.body()?.toString()!!)
                        }
                        isScraped = true
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_on)
                    }
                }
            }

            R.id.btn_product_buy -> {
                val colorList = response.body()?.color
                val sizeList = response.body()?.size
                val price = response.body()?.price

                val bundle = Bundle().apply {
                    putString("colorList",colorList)
                    putString("sizeList",sizeList)
                    putString("price",price.toString())
                    putString("itemId", itemId.toString())
                    putSerializable("response", response.body()!!)
                }

                val buyFragment = BuyFragment()
                buyFragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.vertical_from_bottom,0)
                    .replace(R.id.lt_product_detail_buy, buyFragment)
                    .commit()
            }
            R.id.btn_pd_help -> {
                setPdHelp()
            }
        }
    }
}