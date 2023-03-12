package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.data.model.OrderListItems
import com.example.sejonggoodsmallproject.databinding.ItemOrderCompleteList2Binding

class OrderCompleteListAdapter2(private val context: Context, private val response: List<OrderListItems>)
    : RecyclerView.Adapter<OrderCompleteListAdapter2.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemOrderCompleteList2Binding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListItems) {
//            binding.tvItemOrderCompleteTitle.text = item.
//            binding.tvItemOrderCompleteSeller.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemOrderCompleteList2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(response[position])
    }

    override fun getItemCount() = response.size
}