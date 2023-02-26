package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentMypageBinding
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.login.InitActivity
import com.example.sejonggoodsmallproject.ui.view.search.SearchFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel

class MypageFragment : Fragment() {
    private var _binding : FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteThumbnailAdapter: FavoriteThumbnailAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        binding.btnMypageBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }

        binding.btnMypageLogin.setOnClickListener {
            startActivity(Intent(requireContext(), InitActivity::class.java))
            requireActivity().finish()
        }

        setRvFavoriteThumbnail()

    }

    private fun setRvFavoriteThumbnail() {
        favoriteThumbnailAdapter = FavoriteThumbnailAdapter(requireContext(), emptyList())

        binding.rvFavoriteThumbnail.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = favoriteThumbnailAdapter
        }
    }

    fun onClick(view: View) {
        when (view.id) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}