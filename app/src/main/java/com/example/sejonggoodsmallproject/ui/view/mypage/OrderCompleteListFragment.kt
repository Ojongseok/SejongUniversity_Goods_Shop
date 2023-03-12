package com.example.sejonggoodsmallproject.ui.view.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.data.model.OrderListResponse
import com.example.sejonggoodsmallproject.databinding.FragmentOrderCompleteListBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderCompleteListFragment : Fragment() {
    private var _binding : FragmentOrderCompleteListBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderCompleteListAdapter: OrderCompleteListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var response : List<OrderListResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         _binding = FragmentOrderCompleteListBinding.inflate(inflater, container,false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        CoroutineScope(Dispatchers.IO).launch {
            response = viewModel.getOrderList()

            withContext(Dispatchers.Main) {
                setRvOrderCompleteList()
            }
        }
    }

    private fun setRvOrderCompleteList() {
        orderCompleteListAdapter = OrderCompleteListAdapter(requireContext(), response)

        binding.rvOrderCompleteList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderCompleteListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}