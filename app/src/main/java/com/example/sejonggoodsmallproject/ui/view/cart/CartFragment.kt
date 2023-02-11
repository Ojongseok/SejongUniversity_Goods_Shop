package com.example.sejonggoodsmallproject.ui.view.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.databinding.FragmentCartBinding
import com.example.sejonggoodsmallproject.ui.view.MainActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {
    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainViewModel
    private lateinit var cartListAdapter: CartListAdapter
    private lateinit var responseList : List<CartListResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setBackPressed()
        setCartList()
    }

    private fun setCartList() {
        CoroutineScope(Dispatchers.IO).launch {
            responseList = viewModel.getCartList()

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
    }

    private fun setBackPressed() {
        binding.btnCartBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(0, R.anim.horizon_exit_front)
                    .remove(this@CartFragment).commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}