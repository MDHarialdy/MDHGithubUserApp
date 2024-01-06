package com.belajar.mdh.githubuserapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import com.belajar.mdh.githubuserapp.ui.detail.DetailActivity
import com.belajar.mdhgithubuserapp.databinding.ItemUserBinding

class FavoriteAdapter(private val listUser: List<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteEntity) {
            binding.apply {
                image.load(item.avatarUrl) {
                    transformations(CircleCropTransformation())
                }
                username.text = item.username
                cvItemUser.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_USERNAME, item.username)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}
