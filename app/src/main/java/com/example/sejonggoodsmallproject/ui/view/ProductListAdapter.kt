package com.example.sejonggoodsmallproject.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import com.example.sejonggoodsmallproject.databinding.ItemProductListBinding

class ProductListAdapter(private val context: Context, private var list : List<ProductListResponse>)
    : RecyclerView.Adapter<ProductListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductListResponse) {
            binding.model = item
            Glide.with(context).load(item.img[0].oriImgName).into(binding.ivProduct)
            
            binding.tvProductPrice.text = if (item.price in 1000..999999) {
                val priceList = item.price.toString().toCharArray().toMutableList()
                priceList.add(priceList.size-3,',')
                priceList.joinToString("") + "원"
            } else {
                item.price.toString() + "원"
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }
    fun setData(newList: List<ProductListResponse>) {
        this.list = newList
    }

    override fun getItemCount()= list.size
}