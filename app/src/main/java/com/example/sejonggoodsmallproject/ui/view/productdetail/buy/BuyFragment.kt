package com.example.sejonggoodsmallproject.ui.view.productdetail.buy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.AddCartPost
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.databinding.FragmentBuyBinding
import com.example.sejonggoodsmallproject.ui.view.home.LoginDialog
import com.example.sejonggoodsmallproject.ui.view.login.InitActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.view.search.SearchFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import com.example.sejonggoodsmallproject.util.MyApplication
import kotlinx.android.synthetic.main.dialog_login_confirm.*
import kotlinx.android.synthetic.main.dialog_order_previous_alert.*
import kotlinx.android.synthetic.main.dialog_order_type.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuyFragment : Fragment() {
    private var _binding : FragmentBuyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductDetailViewModel
    private var option1 : String? = "옵션1 선택하기"
    private var option2 : String? = "옵션2 선택하기"
    private var quantity = "1"
    private var color: String? = null
    private var size: String? = null
    private var itemId = 0
    private lateinit var response: ProductDetailResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBuyBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        viewModel = (activity as ProductDetailActivity).viewModel

        itemId = arguments?.getString("itemId","")?.toInt()!!
        response = arguments?.getSerializable("response") as ProductDetailResponse

        setSpinner()

        binding.btnAddCart.setOnClickListener {
            if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
                setLoginDialog()
            } else {
                if (option1 == "옵션1 선택하기" || option2 == "옵션2 선택하기") {
                    Toast.makeText(requireContext(), "옵션을 선택해주세요.",Toast.LENGTH_SHORT).show()
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        color = option1
                        size = option2

                        val response = viewModel.addCart(AddCartPost(quantity, color, size), itemId)

                        withContext(Dispatchers.Main) {
                            when (response.code()) {
                                200 -> {
                                    Toast.makeText(requireContext(), "장바구니에 추가되었습니다.",Toast.LENGTH_SHORT).show()
                                    requireActivity().supportFragmentManager.beginTransaction().remove(this@BuyFragment).commit()
                                }
                                400 -> {
                                    Toast.makeText(requireContext(), "추가에 실패했습니다.",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.btnProductBuy.setOnClickListener {
            if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
                setLoginDialog()
            } else {
                if (option1 == "옵션1 선택하기" || option2 == "옵션2 선택하기") {
                    Toast.makeText(requireContext(), "옵션을 선택해주세요.",Toast.LENGTH_SHORT).show()
                } else {
                    setDialogOrderType()
                }
            }
        }
    }

    private fun setDialogOrderType() {
        val orderTypeDialog = OrderTypeDialog(requireContext())
        orderTypeDialog.showDialog()

        orderTypeDialog.dialog.btn_dialog_order_type_visit.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@BuyFragment).commit()

            orderTypeDialog.dialog.dismiss()

            val bundle = Bundle()
            bundle.apply {
                putString("quantity", quantity)
                putString("option1", option1)
                putString("option2", option2)
                putString("color", color)
                putString("size", size)
                putString("itemId", itemId.toString())
                putString("orderType", "pickup")
                putSerializable("response", response)
            }
            val orderWriteFragment = OrderWriteFragment()
            orderWriteFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front,0)
                .add(R.id.pd_main_container, orderWriteFragment,"backStack")
                .addToBackStack("backStack")
                .commitAllowingStateLoss()
        }

        orderTypeDialog.dialog.btn_dialog_order_type_delivery.setOnClickListener {
            orderTypeDialog.dialog.dismiss()
        }

        orderTypeDialog.dialog.btn_dialog_order_type_close.setOnClickListener {
            orderTypeDialog.dialog.dismiss()
        }
    }

    private fun setLoginDialog() {
        val loginDialog = LoginDialog(requireContext())

        loginDialog.showDialog()

        loginDialog.dialog.btn_dialog_login.setOnClickListener {
            val intent = Intent(requireContext(), InitActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        loginDialog.dialog.btn_dialog_login_close.setOnClickListener {
            loginDialog.dialog.dismiss()
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

            binding.tvBuyPriceSum.text = priceUpdate(arguments?.getString("price")!!.toInt())
            binding.ltBuyOptionPicked.visibility = View.VISIBLE
        }

        // 색상 선택
        if (colorList != null) {
            binding.spinner.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_buy_option,colorList)
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    option1 = binding.spinner.getItemAtPosition(p2).toString()
                    optionPicked()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) { }
            }
        } else {
            option1 = null
        }

        // 사이즈 선택
        if (sizeList != null) {
            binding.spinner2.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_buy_option,sizeList)
            binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    option2 = binding.spinner2.getItemAtPosition(p2).toString()
                    optionPicked()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) { }
            }
        } else {
            option2 = null
        }
    }

    private fun optionPicked() {
        val price = arguments?.getString("price")!!

        binding.tvBuyPickedOption.text = if (option1 != null && option2 != null) {
            "$option1, $option2"
        } else if (option1 != null && option2 == null) {
            "$option1"
        } else if (option1 == null && option2 != null) {
            "$option2"
        } else if (option1 == null && option2 == null) {
            "선택사항 없음"
        } else { "" }

        if (option1 != "옵션1 선택하기" && option2 != "옵션2 선택하기") {
            binding.ltBuyOptionPicked.visibility = View.VISIBLE

            binding.tvBuyAmount.text = "1"
            binding.tvBuyPriceSum.text = priceUpdate(price.toInt())
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
                quantity = cnt.toString()

                binding.tvBuyPriceSum.text = priceUpdate((cnt * price.toInt()))
            }

            R.id.btn_buy_amount_minus -> {
                if(binding.tvBuyAmount.text.toString() != "1") {
                    binding.tvBuyAmount.text = (--cnt).toString()
                    quantity = cnt.toString()

                    binding.tvBuyPriceSum.text = priceUpdate((cnt * price.toInt()))
                }
            }

            R.id.lt_down -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(0,R.anim.vertical_from_top)
                    .remove(this).commit()
            }
        }
    }

    private fun priceUpdate(price: Int) : String {
        if (price in 1000..999999) {
            val priceList = price.toString().toCharArray().toMutableList()
            priceList.add(priceList.size-3,',')
            return priceList.joinToString("") + "원"
        } else {
            return price.toString() + "원"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}