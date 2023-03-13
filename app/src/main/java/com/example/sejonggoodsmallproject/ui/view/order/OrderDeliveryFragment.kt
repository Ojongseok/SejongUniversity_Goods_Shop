package com.example.sejonggoodsmallproject.ui.view.order

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.*
import com.example.sejonggoodsmallproject.databinding.FragmentOrderDeliveryBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.buy.OrderPrevDialog
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel
import kotlinx.android.synthetic.main.dialog_order_previous_alert.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OrderDeliveryFragment : Fragment() {
    private var _binding : FragmentOrderDeliveryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var mainViewModel: MainViewModel
    private var responseCartList =  ArrayList<CartListResponse>()
    private var responseDetailList =  ArrayList<ProductDetailResponse>()
    private var optionPickedList = ArrayList<OptionPicked>()
    private var itemId : Long = 0
    private var orderType = ""
    private lateinit var orderCartProductListAdapter: OrderCartProductListAdapter
    private lateinit var orderDetailProductListAdapter: OrderDetailProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderDeliveryBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStringArg()

        if (orderType == "detail") {
            viewModel = (activity as ProductDetailActivity).viewModel
        } else if (orderType == "cart") {
            mainViewModel = (activity as MainActivity).viewModel
        }

        setRvOrderProduct()

        val childForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data?.getStringExtra("data")
                binding.tvOrderDeliveryAdress1.text = data
            }
        }

        binding.tvOrderDeliveryAdress1.setOnClickListener {
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            childForResult.launch(intent)
        }

        binding.btnOrderDeliveryComplete.setOnClickListener {
            setDialogOrderPrev()
        }

        binding.btnOrderDeliveryBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }
    }
    private fun setDialogOrderPrev() {
        val prevDialog = OrderPrevDialog(requireContext())
        prevDialog.showDialog()

        prevDialog.dialog.btn_dialog_order_prev.setOnClickListener {
            val buyerName = binding.tvOrderDeliveryBuyerName.text.toString()
            val phoneNumber = binding.tvOrderDeliveryPhoneNumber.text.toString()

            if (buyerName.isNotEmpty() && phoneNumber.isNotEmpty()) {
                if (orderType == "detail") {
                    itemId = arguments?.getString("itemId", "0")?.toLong()!!

                    val orderDetailPostInfo = OdpOrderItems(
                        optionPickedList[0].option1,
                        optionPickedList[0].option2,
                        optionPickedList[0].quantity,
                        responseDetailList[0].price
                    )
                    val mlist = mutableListOf<OdpOrderItems>()
                    mlist.add(orderDetailPostInfo)

                    val orderDetailPost = OrderDetailPost(
                        buyerName,
                        phoneNumber,
                        "delivery",
                        OdpAddress(null, null, null),
                        mlist,
                        null
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        val orderResponse = viewModel.postOrderInDetail(orderDetailPost, itemId)

                        withContext(Dispatchers.Main) {
                            if (orderResponse.code() == 200) {
                                val orderCompleteFragment = OrderCompleteFragment()
                                val bundle = Bundle()
                                bundle.apply {
                                    putString("orderType", "detail")
                                    putSerializable("orderResponse", orderResponse.body())
                                    putSerializable("optionPickedList", optionPickedList)
                                    putSerializable("responseDetailList", responseDetailList)
                                }
                                orderCompleteFragment.arguments = bundle

                                requireActivity().supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.horizon_enter_front, 0)
                                    .add(R.id.pd_main_container, orderCompleteFragment, "backStack")
                                    .addToBackStack("backStack")
                                    .commitAllowingStateLoss()
                            }
                        }
                    }
                } else if (orderType == "cart") {
                    val cartIdList = arguments?.getSerializable("cartIdList") as List<Long>
                    val orderCartPost = OrderCartPost(buyerName, phoneNumber, "delivery",
                        OcpAddress(null, null, null),
                        cartIdList,
                        null
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        val orderResponse = mainViewModel.postOrderInCart(orderCartPost)

                        withContext(Dispatchers.Main) {
                            if (orderResponse.code() == 200) {
                                val orderCompleteFragment = OrderCompleteFragment()
                                val bundle = Bundle()
                                bundle.apply {
                                    putString("orderType", "cart")
                                    putSerializable("orderResponse", orderResponse.body())
                                    putSerializable("optionPickedList", optionPickedList)
                                    putSerializable("responseCartList", responseCartList)
                                }
                                orderCompleteFragment.arguments = bundle

                                requireActivity().supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.horizon_enter_front, 0)
                                    .add(R.id.main_container, orderCompleteFragment, "backStack")
                                    .addToBackStack("backStack")
                                    .commitAllowingStateLoss()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "주문 정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            prevDialog.dialog.dismiss()
        }
    }


    private fun setRvOrderProduct() {
        if (orderType == "detail") {
            orderDetailProductListAdapter = OrderDetailProductListAdapter(requireContext(), responseDetailList, optionPickedList)

            binding.rvOrderDeliveryProduct.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = orderDetailProductListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
            }

            binding.btnOrderDeliveryComplete.text = orderDetailProductListAdapter.getPriceString(responseDetailList[0].price * optionPickedList[0].quantity) + " 결제하기"
        } else if (orderType == "cart") {
            orderCartProductListAdapter = OrderCartProductListAdapter(requireContext(), responseCartList, optionPickedList)

            binding.rvOrderDeliveryProduct.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = orderCartProductListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
            }

            var priceSum = 0
            for (i in 0 until responseCartList.size) {
                priceSum += responseCartList[i].price
            }

            binding.btnOrderDeliveryComplete.text = orderCartProductListAdapter.priceUpdate(priceSum) + " 주문하기"
        }
    }

    private fun getStringArg() {
        orderType = arguments?.getString("orderType")!!
        if (orderType == "detail") {
            responseDetailList = arguments?.getSerializable("responseList") as ArrayList<ProductDetailResponse>
        } else if (orderType == "cart") {
            responseCartList = arguments?.getSerializable("responseList") as ArrayList<CartListResponse>
        }
        optionPickedList = arguments?.getSerializable("optionPickedList") as ArrayList<OptionPicked>
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}