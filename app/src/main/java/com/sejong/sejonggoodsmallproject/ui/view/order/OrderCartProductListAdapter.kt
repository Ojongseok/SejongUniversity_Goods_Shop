package com.sejong.sejonggoodsmallproject.ui.view.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sejong.sejonggoodsmallproject.data.model.CartListResponse
import com.sejong.sejonggoodsmallproject.data.model.OptionPicked
import com.sejong.sejonggoodsmallproject.databinding.ItemOrderProductListBinding
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderCartProductListAdapter(
    private var context: Context,
    private var list: List<CartListResponse>,
    private val optionPickedList: ArrayList<OptionPicked>,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<OrderCartProductListAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemOrderProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartListResponse, optionPicked: OptionPicked) {
            binding.tvItemOrderProductTitle.text = item.title
            binding.tvItemOrderCompany.text = item.seller
            Glide.with(context).load(item.repImage.oriImgName).into(binding.ivProduct)

            binding.tvItemOrderPrice.text = priceUpdate(item.price)
            binding.tvItemOrderProductQuantity.text = "수량 ${optionPicked.quantity}개"

            binding.tvItemOrderProductOption.text = if (optionPicked.option1 != null && optionPicked.option2 != null) {
                "${optionPicked.option1}, ${optionPicked.option2}"
            } else if (optionPicked.option1 != null && optionPicked.option2 == null) {
                "${optionPicked.option1}"
            } else if (optionPicked.option1 == null && optionPicked.option2 != null) {
                "${optionPicked.option2}"
            } else if (optionPicked.option1 == null && optionPicked.option2 == null) {
                "선택사항 없음"
            } else { "" }

            CoroutineScope(Dispatchers.IO).launch {
                val result = mainViewModel.getProductDetail(item.itemId.toInt()).body()!!
                withContext(Dispatchers.Main) {
                    val deliveryFee = result.deliveryFee.toString().substring(0,1) + "," + result.deliveryFee.toString().substring(1,4)
                    binding.tvPdDeliveryfee3.text = "${deliveryFee}원"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemOrderProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position], optionPickedList[position])
    }

    fun priceUpdate(price: Int) : String {
        if (price in 1000..999999) {
            val priceList = price.toString().toCharArray().toMutableList()
            priceList.add(priceList.size-3,',')
            return priceList.joinToString("") + "원"
        } else {
            return price.toString() + "원"
        }
    }

    override fun getItemCount() = list.size
}