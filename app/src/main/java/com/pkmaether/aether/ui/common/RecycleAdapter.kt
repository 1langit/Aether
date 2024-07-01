package com.pkmaether.aether.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter<T>(
    private val itemList: List<T>,
    private val onBind: (holder: RecyclerView.ViewHolder, item: T) -> Unit,
    private val onClick: ((item: T) -> Unit)? = null,
    private val onCreateViewHolder: (parent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBind(holder, itemList[position])

        // Set click listener if onClick is provided
        onClick?.let { click ->
            holder.itemView.setOnClickListener {
                click(itemList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}