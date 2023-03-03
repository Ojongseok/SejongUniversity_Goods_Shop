package com.example.sejonggoodsmallproject.ui.view.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.data.model.OptionPicked
import com.example.sejonggoodsmallproject.databinding.FragmentVisitInCartBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.buy.OrderPrevDialog
import com.example.sejonggoodsmallproject.ui.view.order.OrderVisitFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_cart_remove_confirm.*
import kotlinx.android.synthetic.main.dialog_order_previous_alert.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitInCartFragment : Fragment() {
    private var _binding : FragmentVisitInCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainViewModel
    private lateinit var cartListAdapter: CartListAdapter
    private lateinit var responseList : List<CartListResponse>
    private lateinit var filteredList : ArrayList<CartListResponse>
    private lateinit var checkedList : MutableList<Boolean>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVisitInCartBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setCartList()

        binding.btnBuyCompleteCartVisit.setOnClickListener {
            if (checkedList.containsAll(listOf(false))) {
                Toast.makeText(requireContext(), "주문할 상품을 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                setDialogOrderPrev()
            }
        }

        binding.checkboxItemCartAllVisit.setOnClickListener {
            if (filteredList.isNotEmpty()) {
                if (binding.checkboxItemCartAllVisit.isChecked) {
                    cartListAdapter.checkStatusList.forEachIndexed { index, b ->
                        cartListAdapter.checkStatusList[index] = true
                        checkedList[index] = true
                    }
                } else {
                    cartListAdapter.checkStatusList.forEachIndexed { index, b ->
                        cartListAdapter.checkStatusList[index] = false
                        checkedList[index] = false
                    }
                }
                cartListAdapter.rvRefresh()
            }
        }
    }

    private fun setDialogRemove(position: Int) {
        val cartRemoveDialog = CartRemoveDialog(requireContext())
        cartRemoveDialog.showDialog()

        cartRemoveDialog.dialog.btn_cart_dialog_favorite.setOnClickListener {
            Toast.makeText(context,"찜하기 보관",Toast.LENGTH_SHORT).show()
        }

        cartRemoveDialog.dialog.btn_cart_dialog_remove.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                responseList = viewModel.deleteCart(cartListAdapter.getCartId(position))
                filteredList = responseList.filter {
                    it.cartMethod == "pickup"
                }.toMutableList() as ArrayList<CartListResponse>

                checkedList.removeAt(position)
                cartListAdapter.checkStatusList.removeAt(position)

                withContext(Dispatchers.Main) {
                    cartListAdapter.setData(filteredList)
                    cartRemoveDialog.dialog.dismiss()

                    binding.btnBuyCompleteCartVisit.text = calcPriceSum()
                    binding.tvCartListAllVisit.text = "전체 " + filteredList.size.toString() +"개"
                    if (filteredList.isEmpty()) {
                        binding.ivCartEmptyVisit.visibility = View.VISIBLE
                    } else {
                        binding.ivCartEmptyVisit.visibility = View.INVISIBLE
                    }
                }
            }
        }

        cartRemoveDialog.dialog.btn_cart_dialog_close.setOnClickListener {
            cartRemoveDialog.dialog.dismiss()
        }
    }

    private fun setCartList() {
        CoroutineScope(Dispatchers.IO).launch {
            responseList = viewModel.getCartList()
            filteredList = responseList.filter {
                it.cartMethod == "pickup"
            }.toMutableList() as ArrayList<CartListResponse>
            checkedList = MutableList(filteredList.size) { true }

            withContext(Dispatchers.Main) {
                if (filteredList.isNotEmpty()) {
                    setRvCartList()
                    binding.ivCartEmptyVisit.visibility = View.INVISIBLE
                } else {
                    binding.ivCartEmptyVisit.visibility = View.VISIBLE
                }
                binding.tvCartListAllVisit.text = "전체 " + filteredList.size.toString() +"개"
            }
        }
    }

    private fun setRvCartList() {
        cartListAdapter = CartListAdapter(requireContext(), filteredList)

        binding.rvCartListVisit.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
        }

        binding.btnBuyCompleteCartVisit.text = calcPriceSum()

        cartListAdapter.setItemClickListener(object : CartListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) { }
            override fun onClickRemoveBtn(v: View, position: Int) {
                setDialogRemove(position)
            }

            override fun onClickImage(v: View, position: Int) {
                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtra("itemId",cartListAdapter.getCartListItemId(position).toString())
                startActivity(intent)
            }

            override fun onClickPlusBtn(v: View, position: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    var nowQuantity = cartListAdapter.getNowQuantity(position)
                    val response = viewModel.updateCart(cartListAdapter.getCartId(position),++nowQuantity)
                    filteredList[position] = response

                    withContext(Dispatchers.Main) {
                        cartListAdapter.setData(filteredList)

                        binding.btnBuyCompleteCartVisit.text = calcPriceSum()
                    }
                }
            }
            override fun onClickMinusBtn(v: View, position: Int) {
                if(cartListAdapter.getNowQuantity(position) != 1) {
                    CoroutineScope(Dispatchers.IO).launch {
                        var nowQuantity = cartListAdapter.getNowQuantity(position)
                        val response = viewModel.updateCart(cartListAdapter.getCartId(position),--nowQuantity)
                        filteredList[position] = response

                        withContext(Dispatchers.Main) {
                            cartListAdapter.setData(filteredList)

                            binding.btnBuyCompleteCartVisit.text = calcPriceSum()
                        }
                    }
                }
            }
            override fun onClickCheckBoxBtn(position: Int, checkedStatus: Boolean) {
                checkedList[position] = checkedStatus

                binding.btnBuyCompleteCartVisit.text = calcPriceSum()
                binding.checkboxItemCartAllVisit.isChecked = !isCheckedAll()
            }
        })
    }
    private fun setDialogOrderPrev() {
        if (filteredList.isNotEmpty()) {
            val cartOrderPrevDialog = OrderPrevDialog(requireContext())
            cartOrderPrevDialog.showDialog()

            cartOrderPrevDialog.dialog.btn_dialog_order_prev.setOnClickListener {
                val bundle = Bundle()
                val optionPickedList = ArrayList<OptionPicked>()
                val cartIdList = ArrayList<Long>()
                val filterCheckedList = ArrayList<CartListResponse>()

                for (i in 0 until filteredList.size) {
                    if (checkedList[i]) {
                        val optionPicked = OptionPicked(filteredList[i].color, filteredList[i].size, filteredList[i].quantity)
                        optionPickedList.add(optionPicked)

                        cartIdList.add(filteredList[i].id.toLong())
                        filterCheckedList.add(filteredList[i])
                    }
                }

                bundle.apply {
                    putString("orderType", "cart")
                    putSerializable("cartIdList", cartIdList)
                    putSerializable("optionPickedList", optionPickedList)
                    putSerializable("responseList", filterCheckedList)
                }

                val orderVisitFragment = OrderVisitFragment()
                orderVisitFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .add(R.id.main_container, orderVisitFragment,"backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()

                cartOrderPrevDialog.dialog.dismiss()
            }
        } else {
            Toast.makeText(context,"장바구니가 비어있습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isCheckedAll() : Boolean {
        return checkedList.contains(false)
    }

    private fun calcPriceSum() : String {
        var sum = 0
        for (i in checkedList.indices) {
            if (checkedList[i]) {
                sum += filteredList[i].price
            }
        }

        if (sum in 1000..999999) {
            val priceList = sum.toString().toCharArray().toMutableList()
            priceList.add(priceList.size-3,',')
            return priceList.joinToString("") + "원 주문하기"
        } else {
            return sum.toString() + "원 주문하기"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}