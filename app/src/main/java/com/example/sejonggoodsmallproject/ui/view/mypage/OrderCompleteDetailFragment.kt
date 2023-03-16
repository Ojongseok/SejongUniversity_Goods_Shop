package com.example.sejonggoodsmallproject.ui.view.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.OrderListResponse
import com.example.sejonggoodsmallproject.databinding.FragmentOrderCompleteDetailBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel

class OrderCompleteDetailFragment : Fragment() {
    private var _binding : FragmentOrderCompleteDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderCompleteDetailAdapter: OrderCompleteDetailListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderCompleteDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        binding.btnOrderCompleteDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }

        setRv()
    }

    private fun setRv() {
        val response = arguments?.getSerializable("response") as OrderListResponse

        val createStr = response.createdAt.substring(0, 19).toCharArray().filter {
            it in '0'..'9'
        }.joinToString("")
        binding.tvItemOrderCompleteDetailDate.text = "주문번호 : $createStr"

        orderCompleteDetailAdapter = OrderCompleteDetailListAdapter(requireContext(), response, viewModel)

        binding.rvOrderCompleteDetail.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderCompleteDetailAdapter
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}