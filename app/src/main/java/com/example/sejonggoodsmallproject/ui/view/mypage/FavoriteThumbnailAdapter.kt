package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.databinding.ItemFavoriteProductBinding

class FavoriteThumbnailAdapter(private val context: Context, private val list: List<String>)
    : RecyclerView.Adapter<FavoriteThumbnailAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemFavoriteProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            Toast.makeText(context,"$position 터치",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount()= 5
}