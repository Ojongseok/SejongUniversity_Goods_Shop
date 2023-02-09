package com.example.sejonggoodsmallproject.ui.view.productdetail.buy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.AddCartPost
import com.example.sejonggoodsmallproject.databinding.FragmentBuyBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import com.example.sejonggoodsmallproject.util.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuyFragment : Fragment() {
    private var _binding : FragmentBuyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductDetailViewModel
    private var option1 = "옵션1 선택하기"
    private var option2 = "옵션2 선택하기"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBuyBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        viewModel = (activity as ProductDetailActivity).viewModel

        setSpinner()

        binding.btnAddCart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val quantity = binding.tvBuyAmount.text.toString()
                val color = if (option1 == "") {
                    null
                } else { option1 }
                val size = if (option2 == "") {
                    null
                } else { option2 }

                Log.d("태그","$quantity, $color, $size")
                Log.d("태그",MyApplication.prefs.getString("accessToken",""))


                val response = viewModel.addCart(AddCartPost(quantity, color, size))
                Log.d("태그", response.body().toString())
            }
        }
    }

    private fun setSpinner() {
        var colorList : MutableList<String>? = null
        var sizeList : MutableList<String>? = null
        if (arguments?.getString("colorList") != null && arguments?.getString("sizeList") != null) {
            // 색상 O, 사이즈 O
            colorList = arguments?.getString("colorList")!!.split(", ").toMutableList()
            sizeList = arguments?.getString("sizeList")!!.split(", ").toMutableList()

            colorList.add(0, "옵션1 선택하기")
            sizeList.add(0, "옵션2 선택하기")
        } else if (arguments?.getString("colorList") != null && arguments?.getString("sizeList") == null) {
            // 색상 O, 사이즈 X
            colorList = arguments?.getString("colorList")!!.split(", ").toMutableList()
            sizeList = null
            colorList.add(0, "옵션1 선택하기")

            binding.spinner2.visibility = View.GONE
        } else if (arguments?.getString("colorList") == null && arguments?.getString("sizeList") != null) {
            // 색상 X, 사이즈 O
            colorList = null
            sizeList = arguments?.getString("sizeList")!!.split(", ").toMutableList()
            sizeList.add(0, "옵션2 선택하기")

            binding.spinner.visibility = View.GONE
        } else {
            // 색상 X, 사이즈 X
            colorList = null
            sizeList = null

            binding.spinner.visibility = View.GONE
            binding.spinner2.visibility = View.GONE
        }

        if (colorList != null) {
            // 색상 선택
            binding.spinner.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_buy_option,colorList)
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    option1 = binding.spinner.getItemAtPosition(p2).toString()

                    optionPicked()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // 선택되지 않은 경우
                }
            }
        } else {
            option1 = ""
        }
        if (sizeList != null) {
            // 사이즈 선택
            binding.spinner2.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_buy_option,sizeList)
            binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    option2 = binding.spinner2.getItemAtPosition(p2).toString()

                    optionPicked()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        } else {
            option2 = ""
        }
    }

    private fun optionPicked() {
        val price = arguments?.getString("price")!!

        binding.tvBuyPickedOption.text = "$option1, $option2"
        if (option1 != "옵션1 선택하기" && option2 != "옵션2 선택하기") {
            binding.ltBuyOptionPicked.visibility = View.VISIBLE

            binding.tvBuyAmount.text = "1"
            binding.tvBuyPriceSum.text = price


        } else {
            binding.ltBuyOptionPicked.visibility = View.GONE
        }
    }

    fun onClick(view: View) {
        val price = arguments?.getString("price")!!
        var cnt = binding.tvBuyAmount.text.toString().toInt()

        when(view.id) {
            R.id.btn_buy_amout_plus -> {
                binding.tvBuyAmount.text = (++cnt).toString()

                binding.tvBuyPriceSum.text = (cnt * price.toInt()).toString()
            }
            R.id.btn_buy_amount_minus -> {
                binding.tvBuyAmount.text = (--cnt).toString()

                binding.tvBuyPriceSum.text = (cnt * price.toInt()).toString()
            }
            R.id.lt_down -> {
                (activity as ProductDetailActivity).supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}