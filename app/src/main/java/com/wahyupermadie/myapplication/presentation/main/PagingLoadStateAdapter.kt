package com.wahyupermadie.myapplication.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wahyupermadie.myapplication.databinding.ItemNetworkStateBinding
import com.wahyupermadie.myapplication.utils.extension.hideView
import com.wahyupermadie.myapplication.utils.extension.showView

class PagingLoadStateAdapter(
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    private lateinit var loadStateListener: LoadStateListener

    fun setupListener(loadStateListener: LoadStateListener) {
        this.loadStateListener = loadStateListener
    }

    class LoadStateViewHolder(
        private val binding: ItemNetworkStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState, loadStateListener: LoadStateListener) {
            when (loadState) {
                is LoadState.Loading -> binding.progressBar.showView()
                is LoadState.Error -> {
                    binding.progressBar.hideView()
                    loadStateListener.onErrorLoad(loadState.error)
                }
                else -> binding.progressBar.hideView()
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, loadStateListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    interface LoadStateListener {

        fun onErrorLoad(error: Throwable)
    }
}