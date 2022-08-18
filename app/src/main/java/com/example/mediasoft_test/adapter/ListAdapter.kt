package com.example.mediasoft_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediasoft_test.R
import com.example.mediasoft_test.databinding.ListItemBinding
import com.example.mediasoft_test.model.Character


typealias CharacterAction = (Boolean) -> Unit

class ListAdapter(
    private val characterAction: CharacterAction
) :
    PagingDataAdapter<Character, ListAdapter.ListViewHolder>(CharsDiffCallback()) {

    val selectedCharacters = mutableListOf<Character>()
    val selectedPositions = mutableListOf<Int>()
    var selectionMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val character = getItem(position) ?: return
        holder.itemView.setOnLongClickListener {
            onLongClickAction(
                position,
                holder.binding,
                character
            )
        }
        holder.itemView.setOnClickListener { onClickAction(position, holder.binding, character) }
        holder.bind(character)
    }

    class ListViewHolder(
        val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val img: ImageView = itemView.findViewById(R.id.img)

        fun bind(data: Character) {
            if (!data.selected) binding.checkBox.isVisible = false
            binding.characterToBind = data
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

    private fun onLongClickAction(
        pos: Int,
        binding: ListItemBinding,
        character: Character
    ): Boolean {
        if (!selectionMode) {
            selectionMode = true
            character.selected = true
            selectedCharacters.add(character)
            selectedPositions.add(pos)
            characterAction(true)
            binding.checkBox.isVisible = true
        }
        return true
    }


    private fun onClickAction(pos: Int, binding: ListItemBinding, character: Character) {
        if (selectionMode) {
            if (character.selected) {
                selectedCharacters.remove(character)
                selectedPositions.remove(pos)
                character.selected = false
                binding.checkBox.isVisible = false
                if (selectedCharacters.isEmpty()) {
                    selectionMode = false
                    characterAction(false)
                    selectedCharacters.clear()
                }
            } else {
                character.selected = true
                selectedCharacters.add(character)
                selectedPositions.add(pos)
                binding.checkBox.isVisible = true
            }
        }
    }
}
