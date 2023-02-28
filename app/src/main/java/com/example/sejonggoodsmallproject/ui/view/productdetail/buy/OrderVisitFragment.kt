package com.example.sejonggoodsmallproject.ui.view.productdetail.buy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.*
import com.example.sejonggoodsmallproject.databinding.FragmentOrderVisitBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderVisitFragment : Fragment() {
    private var _binding : FragmentOrderVisitBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var response: ProductDetailResponse
    private var itemId: Long = 0
    private var option1: String? = null
    private var option2: String? = null
    private var quantity: Int = 0
    private val orderType = "pickup"
    private lateinit var orderDetailPost: OrderDetailPost
    private lateinit var orderProductListAdapter: OrderProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderVisitBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProductDetailActivity).viewModel

        getStringArg()
        setRvOrderProduct()

        binding.btnOrderComplete.setOnClickListener {
            val buyerName = binding.tvOrderBuyerName.text.toString()
            val phoneNumber = binding.tvOrderBuyerPhoneNumber.text.toString()
            orderDetailPost = OrderDetailPost(buyerName,phoneNumber,orderType, OdpAddress(null,null,null), OdpOrderItems(option1,option2,quantity,response.price), null)

            CoroutineScope(Dispatchers.IO).launch {
                val orderResponse = viewModel.postOrderInDetail(orderDetailPost, itemId)
                Log.d("tag", orderResponse.body().toString())

            }
        }

        binding.btnOrderWriteBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }
    }

    private fun setRvOrderProduct() {
        val list = mutableListOf<ProductDetailResponse>()
        list.add(response)
        val optionPickedList = mutableListOf<OptionPicked>()
        optionPickedList.add(OptionPicked(option1, option2, quantity))
        orderProductListAdapter = OrderProductListAdapter(requireContext(), list, optionPickedList)

        binding.rvOrderProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderProductListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
        }
    }

    private fun getStringArg() {
        response = arguments?.getSerializable("response") as ProductDetailResponse
        itemId = arguments?.getString("itemId")?.toLong()!!
        option1 = arguments?.getString("option1")
        option2 = arguments?.getString("option2")
        quantity = arguments?.getString("quantity")?.toInt()!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}