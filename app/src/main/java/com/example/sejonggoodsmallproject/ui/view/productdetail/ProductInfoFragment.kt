package com.example.sejonggoodsmallproject.ui.view.productdetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentProductInfoBinding
import com.example.sejonggoodsmallproject.databinding.ItemImageviewBinding

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

        binding.rvProductDetailInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ProductDetailInfoImageAdapter(requireContext(), imgList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class ProductDetailInfoImageAdapter(private val context: Context, private val imgList : Array<*>)
    : RecyclerView.Adapter<ProductDetailInfoImageAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemImageviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(context).load(item.substring(48, item.length-1)).into(binding.ivSample)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(imgList[position].toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemImageviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()= imgList.size
}