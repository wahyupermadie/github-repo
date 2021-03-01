package com.wahyupermadie.myapplication.data.usecase.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.databinding.ItemUserBinding

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {

    private var users: List<User> = mutableListOf()
    private lateinit var onClickListener: OnClickListener
    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun addData(users: List<User>) {
        this.users = users
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserAdapter.ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchUserAdapter.ViewHolder, position: Int) {
        holder.bind(users[position], onClickListener, position)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onClickListener: OnClickListener, position: Int) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener.onItemClick(user)
                }
                tvName.text = user.name
                ivAvatar.load(user.avatarUrl)
            }
        }
    }

    interface OnClickListener {

        fun onItemClick(user: User)
    }
}