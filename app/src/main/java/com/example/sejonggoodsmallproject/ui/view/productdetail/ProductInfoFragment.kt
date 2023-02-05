package com.example.sejonggoodsmallproject.ui.view.productdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.model.imgProductDetailInfoResult
import com.example.sejonggoodsmallproject.data.model.imgProductDetailResult
import com.example.sejonggoodsmallproject.databinding.FragmentLoginBinding
import com.example.sejonggoodsmallproject.databinding.FragmentProductInfoBinding

class ProductInfoFragment : Fragment() {
    private var _binding : FragmentProductInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductInfoBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgList = arguments?.getSerializable("imgList") as Array<*>

        imgList.forEach {
            Glide.with(requireContext()).load(it.toString()).into(binding.ivProductDetailInfo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}