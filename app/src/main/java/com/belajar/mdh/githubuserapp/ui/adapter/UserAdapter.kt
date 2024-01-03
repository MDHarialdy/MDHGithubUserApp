package com.belajar.mdh.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.belajar.mdh.githubuserapp.data.model.Item
import com.belajar.mdhgithubuserapp.databinding.ItemViewBinding


class UserAdapter(private val data: MutableList<Item> = mutableListOf(),
                  private val listener: (Item) -> Unit )
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Replace the existing data with the new data
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Item>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemViewBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: Item) {
            v.image.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }
            v.username.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }
}