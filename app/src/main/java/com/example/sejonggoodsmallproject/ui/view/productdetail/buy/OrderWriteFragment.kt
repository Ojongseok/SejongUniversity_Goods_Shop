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
import com.example.sejonggoodsmallproject.data.model.OrderDetailPost
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.databinding.FragmentBuyBinding
import com.example.sejonggoodsmallproject.databinding.FragmentOrderWriteBinding
import com.example.sejonggoodsmallproject.ui.view.home.LoginDialog
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderWriteFragment : Fragment() {
    private var _binding : FragmentOrderWriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var response: ProductDetailResponse
    private var itemId: Long = 0
    private lateinit var orderDetailPost: OrderDetailPost
    private lateinit var orderProductListAdapter: OrderProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderWriteBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProductDetailActivity).viewModel

        response = arguments?.getSerializable("response") as ProductDetailResponse
        itemId = arguments?.getString("itemId")?.toLong()!!

        setRvOrderProduct()

        binding.btnOrderComplete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val orderResponse = viewModel.postOrderInDetail(orderDetailPost, itemId)


            }
        }
    }

    private fun setRvOrderProduct() {
        val list = mutableListOf<ProductDetailResponse>()
        list.add(response)
        orderProductListAdapter = OrderProductListAdapter(requireContext(), list)

        binding.rvOrderProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderProductListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
        }




    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}