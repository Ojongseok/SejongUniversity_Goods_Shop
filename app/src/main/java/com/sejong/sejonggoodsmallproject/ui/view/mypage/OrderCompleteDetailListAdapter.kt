package com.sejong.sejonggoodsmallproject.ui.view.mypage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sejong.sejonggoodsmallproject.data.model.*
import com.sejong.sejonggoodsmallproject.databinding.ItemOrderCompleteDetailBinding
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderCompleteDetailListAdapter(
    private var context: Context,
    private var orderResponse: OrderListResponse,
    private val viewModel: MainViewModel,
) : RecyclerView.Adapter<OrderCompleteDetailListAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemOrderCompleteDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListItems) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = viewModel.getProductDetail(item.itemId.toInt()).body()!!

                withContext(Dispatchers.Main) {
                    binding.tvItemOrderProductTitle.text = result.title
                    binding.tvItemOrderCompany.text = result.seller.name
                    binding.tvOrderCompleteBuyerName.text = orderResponse.buyerName
                    binding.tvOrderCompleteBuyerNumber.text = orderResponse.phoneNumber
                    binding.tvOrderCompleteRequest.text

                    binding.tvOrderCompleteName.text = item.seller.name
                    binding.tvOrderCompleteBank.text = item.seller.bank
                    binding.tvOrderCompleteBankNumber.text = item.seller.account
                    Glide.with(context).load(result.img[0].oriImgName).into(binding.ivProduct)
                    binding.tvOrderCompleteBuyerAddress.text = orderResponse.address?.mainAddress
                    binding.tvOrderCompleteBuyerAddress2.text = orderResponse.address?.detailAddress
//                    binding.tvOrderCompleteRequest.text =

                    binding.tvItemOrderPrice.text = priceUpdate(item.price)
                    binding.tvItemOrderProductQuantity.text = "수량 ${item.quantity}개"

                    binding.tvItemOrderProductOption.text = if (item.color != null && item.size != null) {
                        "${item.color}, ${item.size}"
                    } else if (item.color != null && item.size == null) {
                        "${item.color}"
                    } else if (item.color == null && item.size != null) {
                        "${item.size}"
                    } else if (item.color == null && item.size == null) {
                        "선택사항 없음"
                    } else { "" }


                    binding.tvOrderCompleteDetailFee.text = "${item.deliveryFee}원"

                    binding.btnItemCompleteDetailCopy.setOnClickListener {
                        val getAccount = binding.tvOrderCompleteBankNumber.text.toString()

                        val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip : ClipData = ClipData.newPlainText("getAccount", getAccount)
                        clipboard.setPrimaryClip(clip)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemOrderCompleteDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(orderResponse.orderItems[position])
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

    override fun getItemCount() = orderResponse.orderItems.size
}