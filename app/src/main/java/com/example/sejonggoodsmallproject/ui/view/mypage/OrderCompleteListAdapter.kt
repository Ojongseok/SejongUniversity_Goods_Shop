package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.data.model.OrderListResponse
import com.example.sejonggoodsmallproject.databinding.ItemOrderCompleteListBinding
import com.example.sejonggoodsmallproject.ui.view.home.ProductListAdapter
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.ui.viewmodel.ProductDetailViewModel

class OrderCompleteListAdapter(
    private val context: Context,
    private val response: List<OrderListResponse>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<OrderCompleteListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemOrderCompleteListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListResponse) {
            val createStr = item.createdAt.substring(0, 19).toCharArray().filter {
                it in '0'..'9'
            }.joinToString("")
            binding.tvItemOrderCompleteDate.text = "주문번호 : $createStr"

            binding.rv2OrderCompleteList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = OrderCompleteListAdapter2(context, item.orderItems, viewModel)
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

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun getItemCount() = response.size
}