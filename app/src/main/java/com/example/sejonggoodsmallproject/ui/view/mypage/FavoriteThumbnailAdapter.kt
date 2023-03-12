package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejonggoodsmallproject.data.model.FavoriteListResponse
import com.example.sejonggoodsmallproject.data.model.FavoriteResponse
import com.example.sejonggoodsmallproject.databinding.ItemFavoriteProductBinding
import com.example.sejonggoodsmallproject.ui.view.productdetail.ProductDetailActivity

class FavoriteThumbnailAdapter(private val context: Context, private val list: List<FavoriteListResponse>)
    : RecyclerView.Adapter<FavoriteThumbnailAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemFavoriteProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteListResponse) {
            binding.model = item
            Glide.with(context).load(item.repImage.oriImgName).into(binding.ivItemFavoriteProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("itemId", list[position].itemId.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size
}