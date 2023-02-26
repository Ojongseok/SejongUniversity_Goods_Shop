package com.example.sejonggoodsmallproject.ui.view.productdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentProductInfoBinding
import com.example.sejonggoodsmallproject.databinding.FragmentProductSellerBinding

class ProductSellerFragment : Fragment() {
    private var _binding : FragmentProductSellerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductSellerBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments?.getSerializable("sellerInfo")

        binding.textView3.text = arg.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}