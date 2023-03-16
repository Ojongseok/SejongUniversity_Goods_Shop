package com.sejong.sejonggoodsmallproject.ui.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sejong.sejonggoodsmallproject.data.model.ProductListResponse
import com.sejong.sejonggoodsmallproject.databinding.ItemProductListBinding

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

            binding.tvItemProductSeller.text = when(item.seller.method) {
                "both" -> "현장수령, 택배수령"
                "delivery" -> "택배수령"
                "pickup" -> "현장수령"
                else -> ""
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
        notifyDataSetChanged()
    }

    override fun getItemCount()= list.size
}