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
import com.example.sejonggoodsmallproject.R
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

        binding.tvRecentSearchDeleteAll.setOnClickListener {
            viewModel.deleteRecentSearchedAll()
        }
    }

    private fun setRecyclerViewRecentSearched() {
        recentSearchAdapter = RecentSearchedAdapter(emptyList())

        binding.rvRecentSearch.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentSearchAdapter
        }

        viewModel.getRecentSearchItemsList().observe(viewLifecycleOwner) {
            recentSearchAdapter.setData(it)
        }

        recentSearchAdapter.setItemClickListener(object : RecentSearchedAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                viewModel.deleteRecentSearch(recentSearchAdapter.getItem(position))
            }
        })

    }

    private fun setBackPressed() {
        binding.btnSearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(0, R.anim.horizon_exit_front)
                    .remove(this@SearchFragment).commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}