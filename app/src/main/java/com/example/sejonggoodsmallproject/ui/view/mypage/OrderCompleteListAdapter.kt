package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.data.model.OrderListResponse
import com.example.sejonggoodsmallproject.databinding.ItemOrderCompleteListBinding

class OrderCompleteListAdapter(private val context: Context, private val response: List<OrderListResponse>)
    : RecyclerView.Adapter<OrderCompleteListAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemOrderCompleteListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListResponse) {
            binding.tvItemOrderCompleteDate.text = item.createdAt

            binding.rv2OrderCompleteList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = OrderCompleteListAdapter2(context, item.orderItems)
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemOrderCompleteListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(response[position])

    }

    override fun getItemCount() = response.size
}