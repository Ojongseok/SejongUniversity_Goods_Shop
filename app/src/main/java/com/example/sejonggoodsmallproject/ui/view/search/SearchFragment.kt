package com.example.sejonggoodsmallproject.ui.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.databinding.FragmentSearchBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
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
        binding.fragment = this

        setRvRecentSearched()
    }

    private fun setRvRecentSearched() {
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
            override fun onClickRemove(v: View, position: Int) {
                viewModel.deleteRecentSearch(recentSearchAdapter.getItem(position))
            }
            override fun onClickTitle(v: View, position: Int) {
                val bundle = Bundle().apply {
                    putString("searchKeyWord", recentSearchAdapter.getTitle(position))
                }
                val searchResultFragment = SearchResultFragment()
                searchResultFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front,0)
                    .add(R.id.main_container, searchResultFragment,"tag")
                    .addToBackStack("tag")
                    .commitAllowingStateLoss()

                binding.etSearchBar.setText("")
            }
        })
    }

    fun onClick(view:View) {
        when (view.id) {
            R.id.btn_search_complete -> {
                val searchKeyWord = binding.etSearchBar.text.toString()
                val timestamp = SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(System.currentTimeMillis()))

                if (searchKeyWord.isEmpty()) {
                    Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insertRecentSearch(RecentSearchModel(0, searchKeyWord, timestamp))

                    val bundle = Bundle().apply {
                        putString("searchKeyWord", searchKeyWord)
                    }

                    val searchResultFragment = SearchResultFragment()
                    searchResultFragment.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.horizon_enter_front,0)
                        .add(R.id.main_container, searchResultFragment, "backStack")
                        .addToBackStack("backStack")
                        .commitAllowingStateLoss()

                    binding.etSearchBar.setText("")
                }
            }
            R.id.tv_recent_search_delete_all -> {
                viewModel.deleteRecentSearchedAll()
            }
            R.id.btn_search_back -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(0, R.anim.horizon_exit_front)
                    .remove(this).commit()

                requireActivity().onBackPressed()
            }
            R.id.lt_search_fragment -> {
                hideKeyboard()
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