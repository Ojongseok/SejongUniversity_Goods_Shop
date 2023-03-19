package com.sejong.sejonggoodsmallproject.ui.view.order

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sejong.sejonggoodsmallproject.data.model.*
import com.sejong.sejonggoodsmallproject.databinding.FragmentOrderCompleteBinding
import com.sejong.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderCompleteFragment : Fragment() {
    private var _binding : FragmentOrderCompleteBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderCompleteAdapter : OrderCompleteProductListAdapter
    private lateinit var orderCompleteDetailAdapter : OrderCompleteDetailAdapter
    private lateinit var orderDetailResponse : OrderDetailResponse
    private lateinit var optionPickedList : ArrayList<OptionPicked>
    private var responseCartList =  ArrayList<CartListResponse>()
    private var responseDetailList =  ArrayList<ProductDetailResponse>()
    private var orderType = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderCompleteBinding.inflate(inflater, container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArg()
        setRV()

        binding.btnOrderCompleteBack.setOnClickListener {
            if (orderType == "detail") {
                (activity as ProductDetailActivity).finish()
            } else if (orderType == "cart") {
                requireActivity().supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        binding.btnGoOrderList.setOnClickListener {
            if (orderType == "detail") {
                (activity as ProductDetailActivity).finish()
            } else if (orderType == "cart") {
                requireActivity().supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            }
        }
        val createStr = orderDetailResponse.createdAt
        val cal = Calendar.getInstance()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val date: Date = df.parse(createStr)
        cal.time = date
        cal.add(Calendar.HOUR_OF_DAY, 9)

        val reDate = df.format(cal.time).toString().replace("-", ".").replace('T', ' ')

        binding.tvOrderCompleteDeadline.text = "주문일자 : $reDate"
    }

    private fun setRV() {
        if (orderType == "detail") {
            orderCompleteDetailAdapter = OrderCompleteDetailAdapter(requireContext(),orderDetailResponse,optionPickedList,responseDetailList)

            binding.rvOrderComplete.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = orderCompleteDetailAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
            }
        } else if (orderType == "cart") {
            orderCompleteAdapter = OrderCompleteProductListAdapter(requireContext(), orderDetailResponse, optionPickedList, responseCartList)

            binding.rvOrderComplete.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = orderCompleteAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
            }
        }
    }

    private fun getArg() {
        orderType = arguments?.getString("orderType").toString()
        orderDetailResponse = arguments?.getSerializable("orderResponse") as OrderDetailResponse
        optionPickedList = arguments?.getSerializable("optionPickedList") as ArrayList<OptionPicked>

        if (orderType == "detail") {
            responseDetailList = arguments?.getSerializable("responseDetailList") as ArrayList<ProductDetailResponse>
        } else if (orderType == "cart") {
            responseCartList = arguments?.getSerializable("responseCartList") as ArrayList<CartListResponse>
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}