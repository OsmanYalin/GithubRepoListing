package com.osmanyalin.githubrepolisting.ui.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osmanyalin.githubrepolisting.databinding.ItemRepoBinding
import com.osmanyalin.githubrepolisting.model.RepoModel

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    private lateinit var itemClickListener: ((RepoModel) -> Unit)
    private val items: MutableList<RepoModel> = mutableListOf()

    fun onItemClick(clickListener: ((RepoModel) -> Unit)) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if(::itemClickListener.isInitialized) {
                itemClickListener.invoke(items[position])
            }
        }

        holder.bind(items[position])
    }

    class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepoModel) {
            binding.repo = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup): RepoViewHolder {
                val binding = ItemRepoBinding.inflate(inflater, parent, false)

                return RepoViewHolder(binding)
            }
        }
    }
}