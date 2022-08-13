package com.example.mediasoft_test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mediasoft_test.R
import com.example.mediasoft_test.databinding.ListFragmentBinding
import com.example.mediasoft_test.databinding.ListItemBinding
import com.example.mediasoft_test.databinding.LoadStateItemBinding

class ListLoadStateAdapter(): LoadStateAdapter<ListLoadStateAdapter.Holder>() {

    class Holder(
        private val binding: LoadStateItemBinding,
        private val refreshLayout: SwipeRefreshLayout?
    ): RecyclerView.ViewHolder(binding.root) {

        //private val statusImg = binding.status


        fun bind(state: LoadState) = with(binding) {
            if ( refreshLayout != null ) {
                Log.d("aboba", "refreshLayout")
                refreshLayout.isRefreshing = state is LoadState.Loading
            }
            /*if (state is LoadState.Error) {
                statusImg.setImageResource(R.drawable.error)
            }
            else if (state is LoadState.Loading) {
                Log.d("aboba", "loading")
                statusImg.setImageResource(R.drawable.loading_animation)
            }*/
            progressBar.isVisible = state is LoadState.Loading
            error.isVisible = state is LoadState.Error
        }

    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoadStateItemBinding.inflate(inflater, parent, false)
        return Holder(binding, null)
    }
}