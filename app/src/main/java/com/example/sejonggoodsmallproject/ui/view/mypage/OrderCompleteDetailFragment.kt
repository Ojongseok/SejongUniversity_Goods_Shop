package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentOrderCompleteDetailBinding
import com.example.sejonggoodsmallproject.databinding.FragmentOrderCompleteListBinding
import com.example.sejonggoodsmallproject.ui.view.home.ProductListAdapter
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity

class OrderCompleteDetailFragment : Fragment() {
    private var _binding : FragmentOrderCompleteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderCompleteDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOrderCompleteDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}