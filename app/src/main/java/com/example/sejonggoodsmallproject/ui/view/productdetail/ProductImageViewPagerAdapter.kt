package com.example.sejonggoodsmallproject.ui.view.productdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.model.imgProductDetailResult
import com.example.sejonggoodsmallproject.databinding.ItemProductSomenailBinding

class ProductImageViewPagerAdapter(private val context: Context, private val imageList: List<imgProductDetailResult>)
    : RecyclerView.Adapter<ProductImageViewPagerAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemProductSomenailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: imgProductDetailResult) {
            Glide.with(context).load(item.oriImgName).into(binding.ivProductSomenail)
        }
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemProductSomenailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = imageList.size
}