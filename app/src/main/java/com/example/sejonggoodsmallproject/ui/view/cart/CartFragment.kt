package com.example.sejonggoodsmallproject.ui.view.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.databinding.FragmentCartBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.buy.OrderPrevDialog
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_cart_remove_confirm.*
import kotlinx.android.synthetic.main.dialog_order_previous_alert.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {
    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainViewModel
    private lateinit var cartListAdapter: CartListAdapter
    private lateinit var responseList : MutableList<CartListResponse>
    private lateinit var checkedList : MutableList<Boolean>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setCartList()

        binding.btnCartBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }

        binding.btnBuyComplete.setOnClickListener {
            setDialogOrderPrev()
        }

        binding.checkboxItemCartAll.setOnClickListener {
            if (responseList.isNotEmpty()) {
                if (binding.checkboxItemCartAll.isChecked) {
                    if (responseList.isNotEmpty()) {
                        cartListAdapter.checkStatusList.forEachIndexed { index, b ->
                            cartListAdapter.checkStatusList[index] = true
                            checkedList[index] = true
                        }
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
                responseList = viewModel.deleteCart(cartListAdapter.getCartId(position)).toMutableList()
                checkedList.removeAt(position)
                cartListAdapter.checkStatusList.removeAt(position)

                withContext(Dispatchers.Main) {
                    cartListAdapter.setData(responseList)
                    cartRemoveDialog.dialog.dismiss()

                    binding.btnBuyComplete.text = calcPriceSum()
                    binding.tvCartListAll.text = "전체 " + responseList.size.toString() +"개"
                }
            }
        }

        cartRemoveDialog.dialog.btn_cart_dialog_close.setOnClickListener {
            cartRemoveDialog.dialog.dismiss()
        }
    }

    private fun setCartList() {
        CoroutineScope(Dispatchers.IO).launch {
            responseList = viewModel.getCartList().toMutableList()
            checkedList = MutableList(responseList.size) { true }

            withContext(Dispatchers.Main) {
                if (responseList.isNotEmpty()) {
                    setRvCartList()
                    binding.ivEmptyCart.visibility = View.INVISIBLE
                } else {
                    binding.ivEmptyCart.visibility = View.VISIBLE
                }
                binding.tvCartListAll.text = "전체 " + responseList.size.toString() +"개"
            }
        }
    }

    private fun setRvCartList() {
        cartListAdapter = CartListAdapter(requireContext(), responseList)

        binding.rvCartList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
        }

        binding.btnBuyComplete.text = calcPriceSum()

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
                    responseList[position] = response

                    withContext(Dispatchers.Main) {
                        cartListAdapter.setData(responseList)

                        binding.btnBuyComplete.text = calcPriceSum()
                    }
                }
            }

            override fun onClickMinusBtn(v: View, position: Int) {
                if(cartListAdapter.getNowQuantity(position) != 1) {
                    CoroutineScope(Dispatchers.IO).launch {
                        var nowQuantity = cartListAdapter.getNowQuantity(position)
                        val response = viewModel.updateCart(cartListAdapter.getCartId(position),--nowQuantity)
                        responseList[position] = response

                        withContext(Dispatchers.Main) {
                            cartListAdapter.setData(responseList)

                            binding.btnBuyComplete.text = calcPriceSum()
                        }
                    }
                }
            }

            override fun onClickCheckBoxBtn(position: Int, checkedStatus: Boolean) {
                checkedList[position] = checkedStatus

                binding.btnBuyComplete.text = calcPriceSum()

                binding.checkboxItemCartAll.isChecked = !isCheckedAll()
            }
        })
    }

    private fun isCheckedAll() : Boolean {
        return checkedList.contains(false)
    }

    private fun calcPriceSum() : String {
        var sum = 0
        for (i in checkedList.indices) {
            if (checkedList[i]) {
                sum += responseList[i].price
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

    private fun setDialogOrderPrev() {
        if (responseList.isNotEmpty()) {
            val cartOrderPrevDialog = OrderPrevDialog(requireContext())
            cartOrderPrevDialog.showDialog()

            cartOrderPrevDialog.dialog.btn_dialog_order_prev.setOnClickListener {
                cartOrderPrevDialog.dialog.dismiss()
            }
        } else {
            Toast.makeText(context,"장바구니가 비어있습니다.",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}