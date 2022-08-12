package com.example.mediasoft_test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediasoft_test.R
import com.example.mediasoft_test.databinding.ListItemBinding
import com.example.mediasoft_test.model.Character

class ListAdapter() :
    PagingDataAdapter<Character, ListAdapter.ListViewHolder>(CharsDiffCallback()) {

    //var characterList = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val character = getItem(position) ?: return
        holder.bind(character)
    }

    //override fun getItemCount() = characterList.size

    class ListViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        private val img: ImageView = itemView.findViewById(R.id.img)
        //val charName: TextView = itemView.findViewById(R.id.name)
        //val description: TextView = itemView.findViewById(R.id.description)

        fun bind(data: Character) {
            binding.character = data
            loadPhoto(data.img, img)
        }

        private fun loadPhoto(imgStr: String, imageView: ImageView) {
            imgStr.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                Glide.with(imageView.context)
                    .load(imgUri)
                    .placeholder(R.drawable.loading_animation)
                    .circleCrop()
                    .error(R.drawable.error)
                    .into(imageView)
            }
        }
    }

    class CharsDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }

}