package com.example.mediasoft_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mediasoft_test.databinding.ListItemBinding

class ListLoadStateAdapter(): LoadStateAdapter<ListLoadStateAdapter.Holder>() {

    class Holder(
        private val binding: ListItemBinding,
        private val refreshLayout: SwipeRefreshLayout?
    ): RecyclerView.ViewHolder(binding.root) {




        fun bind(state: LoadState) = with(binding) {
            if ( refreshLayout != null ) {
                refreshLayout.isRefreshing = state is LoadState.Loading
            }

        }

    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return Holder(binding, null)
    }
}