package com.example.sejonggoodsmallproject.ui.view.productdetail.buy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.databinding.ItemOrderProductListBinding
import com.example.sejonggoodsmallproject.databinding.ItemRecentSearchBinding
import kotlinx.android.synthetic.main.item_recent_search.view.*

class OrderProductListAdapter(private var context: Context, private var list : List<ProductDetailResponse>)
    : RecyclerView.Adapter<OrderProductListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(private val binding: ItemOrderProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetailResponse) {
            binding.tvItemOrderProductTitle.text = item.title
            Glide.with(context).load(item.img[0].oriImgName).into(binding.ivProduct)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemOrderProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderProductListAdapter.CustomViewHolder, position: Int) {
        holder.bind(list[position])

    }

    fun setData(newData: List<ProductDetailResponse>) {
        list = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}