package com.sejong.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sejong.sejonggoodsmallproject.data.model.OrderListResponse
import com.sejong.sejonggoodsmallproject.databinding.ItemOrderCompleteListBinding
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderCompleteListAdapter(
    private val context: Context,
    private val response: List<OrderListResponse>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<OrderCompleteListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemOrderCompleteListBinding): RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: OrderListResponse) {

            val cal = Calendar.getInstance()
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
            val date: Date = df.parse(item.createdAt)
            cal.time = date
            cal.add(Calendar.HOUR_OF_DAY,9)

            val reDate = df.format(cal.time).toString().replace("-", ".").replace('T', ' ')

            binding.tvItemOrderCompleteDate.text = "주문일자 : $reDate"

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

    @RequiresApi(Build.VERSION_CODES.N)
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