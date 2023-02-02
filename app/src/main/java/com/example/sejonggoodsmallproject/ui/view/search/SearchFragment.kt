package com.example.sejonggoodsmallproject.ui.view.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.databinding.FragmentSearchBinding
import com.example.sejonggoodsmallproject.ui.view.MainActivity
import com.example.sejonggoodsmallproject.ui.view.ProductListAdapter
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainViewModel
    private lateinit var recentSearchAdapter : RecentSearchedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setBackPressed()
        setRecyclerViewRecentSearched()



        binding.btnSearchComplete.setOnClickListener {
            val title = binding.etSearchBar.text.toString()
            val timestamp = SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(System.currentTimeMillis()))

            viewModel.insertRecentSearch(RecentSearchModel(0, title, timestamp))
        }
    }

    private fun setRecyclerViewRecentSearched() {
        recentSearchAdapter = RecentSearchedAdapter(emptyList())

        viewModel.getRecentSearchItemsList().observe(viewLifecycleOwner) {
            recentSearchAdapter = RecentSearchedAdapter(it)
            binding.rvRecentSearch.adapter = recentSearchAdapter
        }

        binding.rvRecentSearch.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentSearchAdapter
        }

        recentSearchAdapter.setItemClickListener(object : RecentSearchedAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Toast.makeText(requireContext(),"$position 터치",Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setBackPressed() {
        binding.btnSearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@SearchFragment).commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}