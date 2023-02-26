package com.example.sejonggoodsmallproject.ui.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.MemberIdPost
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import com.example.sejonggoodsmallproject.databinding.FragmentSearchBinding
import com.example.sejonggoodsmallproject.databinding.FragmentSearchResultBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.home.ProductListAdapter
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultFragment : Fragment() {
    private var _binding : FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainViewModel
    private lateinit var searchResultListAdapter : ProductListAdapter
    private lateinit var response : List<ProductListResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        hideKeyboard()

        binding.etSearchResultBar.text = arguments?.getString("searchKeyWord","")

        binding.btnSearchResultBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this@SearchResultFragment)
                .commit()

            requireActivity().onBackPressed()
        }
        setRvSearchResult()
    }

    private fun setRvSearchResult() {
        CoroutineScope(Dispatchers.IO).launch {
            response = viewModel.getAllProducts(MemberIdPost(0))

            val keyword = arguments?.getString("searchKeyWord","")
            val searchResponse = response.filter {
                it.title.contains(keyword.toString())
                it.description.contains(keyword.toString())
            }

            searchResultListAdapter = ProductListAdapter(requireContext(), searchResponse)

            withContext(Dispatchers.Main) {
                binding.rvSearchResultList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = searchResultListAdapter
                    addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
                }

                searchResultListAdapter.setItemClickListener(object : ProductListAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int) {
                        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                        intent.putExtra("itemId", searchResponse[position].id.toString())
                        startActivity(intent)
                    }
                })
            }
        }
    }

    private fun hideKeyboard() {
        if (requireActivity().currentFocus != null) {
            // 프래그먼트기 때문에 getActivity() 사용
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}