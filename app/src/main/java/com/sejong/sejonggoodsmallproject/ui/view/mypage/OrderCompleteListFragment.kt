package com.sejong.sejonggoodsmallproject.ui.view.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.data.model.OrderListResponse
import com.sejong.sejonggoodsmallproject.databinding.FragmentOrderCompleteListBinding
import com.sejong.sejonggoodsmallproject.ui.view.home.MainActivity
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
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

        binding.btnOrderCompleteListBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }
    }

    private fun setRvOrderCompleteList() {
        orderCompleteListAdapter = OrderCompleteListAdapter(requireContext(), response, viewModel)

        binding.rvOrderCompleteList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderCompleteListAdapter
        }

        orderCompleteListAdapter.setItemClickListener(object : OrderCompleteListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val frg = OrderCompleteDetailFragment()
                val bundle = Bundle()
                bundle.putSerializable("response", response[position])
                frg.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .add(R.id.main_container, frg,"backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()

            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}