package com.example.sejonggoodsmallproject.ui.view

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
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.databinding.FragmentHomeBinding
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setTabLayout()
    }

    private fun setRvProductList() {
        val list = ArrayList<ProductListData>()
        list.add(ProductListData("이름1","새내기1",15000, listOf("123","123")))
        list.add(ProductListData("이름2","새내기2",3000, listOf("123","123")))
        list.add(ProductListData("이름3","새내기3",42000, listOf("123","123")))
        productListAdapter = ProductListAdapter(requireContext(), list)

        binding.rvProductList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager(requireContext()).orientation))
        }

        productListAdapter.setItemClickListener(object : ProductListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Toast.makeText(context, position.toString(),Toast.LENGTH_SHORT).show()

                val intent = Intent(context,ProductDetailActivity::class.java)
                context?.startActivity(intent)
            }
        })
    }

    private fun setTabLayout() {
        // 초기 tab 세팅
        setRvProductList()

        binding.storeFragmentTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // tab이 선택되었을 때
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> Toast.makeText(requireContext(),"전체상품 클릭",Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(requireContext(),"의류 클릭",Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(requireContext(),"문구 클릭",Toast.LENGTH_SHORT).show()
                }
            }
            // tab이 선택되지 않았을 때
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            // tab이 다시 선택되었을 때
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}