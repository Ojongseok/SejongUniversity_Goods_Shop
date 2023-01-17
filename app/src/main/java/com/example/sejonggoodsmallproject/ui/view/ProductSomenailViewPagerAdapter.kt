package com.example.sejonggoodsmallproject.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.databinding.ItemProductSomenailBinding

class ProductSomenailViewPagerAdapter(private val context: Context, private val imageList: List<String>)
    : RecyclerView.Adapter<ProductSomenailViewPagerAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemProductSomenailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(context).load(item).into(binding.ivProductSomenail)
        }
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSomenailViewPagerAdapter.CustomViewHolder {
        val view = ItemProductSomenailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = imageList.size
}