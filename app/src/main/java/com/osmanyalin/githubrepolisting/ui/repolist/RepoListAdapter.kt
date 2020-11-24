package com.osmanyalin.githubrepolisting.ui.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osmanyalin.githubrepolisting.databinding.ItemRepoBinding
import com.osmanyalin.githubrepolisting.model.RepoModel

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.ContentViewHolder>() {

    private lateinit var itemClickListener: ((RepoModel) -> Unit)
    private val items: MutableList<RepoModel> = mutableListOf()

    fun onClick(clickListener: ((RepoModel) -> Unit)) {
        itemClickListener = clickListener
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<RepoModel>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder.create(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if(::itemClickListener.isInitialized) {
                itemClickListener.invoke(items[position])
            }
        }
        holder.bind(items[position])
    }

    class ContentViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepoModel) {
            binding.data = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup): ContentViewHolder {
                val binding = ItemRepoBinding.inflate(inflater, parent, false)

                return ContentViewHolder(binding)
            }
        }
    }
}

