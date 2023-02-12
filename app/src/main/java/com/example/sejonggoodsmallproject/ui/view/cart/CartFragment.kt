package com.example.sejonggoodsmallproject.ui.view.cart

import android.content.Context
import android.os.Bundle
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
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {
    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback: OnBackPressedCallback
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

        setCartList()

        binding.btnCartBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(0, R.anim.horizon_exit_front)
                    .remove(this@CartFragment)
                    .commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}