package com.example.sejonggoodsmallproject.ui.view.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.sejonggoodsmallproject.data.model.CartListResponse
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.databinding.ItemCartListBinding
import kotlinx.android.synthetic.main.item_cart_list.view.*

class CartListAdapter(private val context: Context, private var list : List<CartListResponse>)
    : RecyclerView.Adapter<CartListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemCartListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartListResponse) {
            Glide.with(context).load(item.repImage.oriImgName).into(binding.ivItemCart)

            binding.tvItemCartTitle.text = item.title
            binding.tvItemCartAmount.text = item.quantity.toString()

            binding.tvItemCartOption.text = if (item.color != null && item.size != null) {
                "${item.color}, ${item.size}"
            } else if (item.color != null && item.size == null) {
                "${item.color}"
            } else if (item.color == null && item.size != null) {
                "${item.size}"
            } else { "" }

            binding.tvItemCartPrice.text = if (item.price in 1000..999999) {
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
        holder.itemView.btn_cart_list_remove.setOnClickListener {
            itemClickListener.onClickRemoveBtn(it, position)
        }
        holder.itemView.iv_item_cart_cardview.setOnClickListener {
            itemClickListener.onClickImage(it, position)
        }
        holder.itemView.btn_item_cart_amount_plus.setOnClickListener {
            itemClickListener.onClickPlusBtn(it, position)
        }
        holder.itemView.btn_item_cart_amount_minus.setOnClickListener {
            itemClickListener.onClickMinusBtn(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
        fun onClickRemoveBtn(v: View, position: Int)
        fun onClickImage(v: View, position: Int)
        fun onClickPlusBtn(v: View, position: Int)
        fun onClickMinusBtn(v: View, position: Int)
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
    fun getCartListItemId(position: Int) : Long {
        return list[position].itemId
    }
    fun getNowQuantity(position: Int) : Int {
        return list[position].quantity
    }
    override fun getItemCount()= list.size
}