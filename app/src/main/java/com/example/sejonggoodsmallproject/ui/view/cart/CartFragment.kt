package com.example.sejonggoodsmallproject.ui.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.databinding.FragmentCartBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_cart_remove_confirm.*
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
        }
    }

    private fun setDialog(position: Int) {
        val cartRemoveDialog = CartRemoveDialog(requireContext())

        cartRemoveDialog.showDialog()

        cartRemoveDialog.dialog.btn_cart_dialog_favorite.setOnClickListener {
            Toast.makeText(context,"찜하기 보관",Toast.LENGTH_SHORT).show()
        }

        cartRemoveDialog.dialog.btn_cart_dialog_remove.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                responseList = viewModel.deleteCart(cartListAdapter.getCartId(position)).toMutableList()

                withContext(Dispatchers.Main) {
                    cartListAdapter.setData(responseList)
                    cartRemoveDialog.dialog.dismiss()
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

            withContext(Dispatchers.Main) {
                if (responseList.isNotEmpty()) {
                    setRvCartList()
                    binding.ivEmptyCart.visibility = View.INVISIBLE
                } else {
                    binding.ivEmptyCart.visibility = View.VISIBLE
                }
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

        cartListAdapter.setItemClickListener(object : CartListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
            }
            override fun onClickRemoveBtn(v: View, position: Int) {
                setDialog(position)
            }
            override fun onClickImage(v: View, position: Int) {
                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtra("itemId",cartListAdapter.getCartListItemId(position).toString())
                startActivity(intent)
            }

            override fun onClickPlusBtn(v: View, position: Int) {
                Toast.makeText(requireContext(), "수량이 변경되었습니다.", Toast.LENGTH_SHORT).show()

                CoroutineScope(Dispatchers.IO).launch {
                    var nowQuantity = cartListAdapter.getNowQuantity(position)
                    val response = viewModel.updateCart(cartListAdapter.getCartId(position),++nowQuantity)
                    responseList[position] = response

                    withContext(Dispatchers.Main) {
                        cartListAdapter.setData(responseList)
                    }
                }
            }

            override fun onClickMinusBtn(v: View, position: Int) {
                if(cartListAdapter.getNowQuantity(position) != 1) {
                    Toast.makeText(requireContext(), "수량이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                    
                    CoroutineScope(Dispatchers.IO).launch {
                        var nowQuantity = cartListAdapter.getNowQuantity(position)
                        val response = viewModel.updateCart(cartListAdapter.getCartId(position),--nowQuantity)
                        responseList[position] = response

                        withContext(Dispatchers.Main) {
                            cartListAdapter.setData(responseList)
                        }
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}