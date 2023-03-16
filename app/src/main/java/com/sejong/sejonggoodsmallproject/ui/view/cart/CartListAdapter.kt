package com.sejong.sejonggoodsmallproject.ui.view.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sejong.sejonggoodsmallproject.data.model.CartListResponse
import com.sejong.sejonggoodsmallproject.databinding.ItemCartListBinding
import kotlinx.android.synthetic.main.item_cart_list.view.*

class CartListAdapter(private val context: Context, private var list : List<CartListResponse>)
    : RecyclerView.Adapter<CartListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    var checkStatusList = MutableList(list.size) { true }

    inner class CustomViewHolder(private val binding: ItemCartListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartListResponse, status: Boolean) {
            binding.model = item

            Glide.with(context).load(item.repImage.oriImgName).into(binding.ivItemCart)

            binding.tvItemCartOption.text = if (item.color != null && item.size != null) {
                "${item.color}, ${item.size}"
            } else if (item.color != null && item.size == null) {
                "${item.color}"
            } else if (item.color == null && item.size != null) {
                "${item.size}"
            } else { "선택사항 없음" }

            binding.tvItemCartPrice.text = if (item.price in 1000..999999) {
                val priceList = item.price.toString().toCharArray().toMutableList()
                priceList.add(priceList.size-3,',')
                priceList.joinToString("") + "원"
            } else {
                item.price.toString() + "원"
            }

            binding.checkboxItemCart.isChecked = status
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position], checkStatusList[position])

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
        holder.itemView.checkbox_item_cart.setOnCheckedChangeListener { _, isChecked ->
            if (holder.itemView.checkbox_item_cart.isChecked) {
                itemClickListener.onClickCheckBoxBtn(position, true)
                checkStatusList[position] = true
            } else {
                itemClickListener.onClickCheckBoxBtn(position, false)
                checkStatusList[position] = false
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
        fun onClickRemoveBtn(v: View, position: Int)
        fun onClickImage(v: View, position: Int)
        fun onClickPlusBtn(v: View, position: Int)
        fun onClickMinusBtn(v: View, position: Int)
        fun onClickCheckBoxBtn(position: Int, checkedStatus: Boolean)
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

    fun rvRefresh() {
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