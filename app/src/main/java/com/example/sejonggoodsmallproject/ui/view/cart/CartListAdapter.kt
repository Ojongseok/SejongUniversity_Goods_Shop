package com.example.sejonggoodsmallproject.ui.view.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.databinding.ItemCartListBinding
import kotlinx.android.synthetic.main.item_cart_list.view.*

class CartListAdapter(private val context: Context, private var list : List<CartListResponse>)
    : RecyclerView.Adapter<CartListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemCartListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartListResponse) {
//            Glide.with(context).load(item.repImage.oriImgName).into(binding.ivItemCart)
            binding.tvItemCartTitle.text = item.title
            binding.tvItemCartAmount.text = item.quantity.toString()
            binding.tvItemCartOption.text = item.color + ", " + item.size

            binding.tvItemCartPrice.text = item.price.toString()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
        holder.itemView.btn_cart_list_remove.setOnClickListener {
            itemClickListener.onClickRemoveBtn(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
        fun onClickRemoveBtn(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemCartListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun setData(newData: List<CartListResponse>) {
        list = newData
        notifyDataSetChanged()
    }

    fun getCartId(position: Int) : Long {
        return list[position].id.toLong()
    }

    override fun getItemCount()= list.size
}