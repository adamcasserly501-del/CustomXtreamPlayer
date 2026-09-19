package com.custom.xtreamplayer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChannelAdapter(
    private val channels: List<ChannelEntity>,
    private val isLiveTv: Boolean = true,
    private val onClick: (ChannelEntity) -> Unit,
    private val onLongClick: ((ChannelEntity) -> Unit)? = null
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    // Private property prevents Kotlin auto-generating a conflicting JVM setter
    private var selectedPosition = -1

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvChannelName: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        view.isFocusable = true
        view.isClickable = true
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channels[position]
        
        holder.tvChannelName.text = channel.name
        holder.tvChannelName.setTextColor(Color.WHITE)

        // Highlights active channel in custom blue (#03A9F4)
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#03A9F4"))
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.itemView.setOnClickListener {
            setSelectedPosition(holder.bindingAdapterPosition)
            onClick(channel)
        }

        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(channel)
            true
        }
    }

    fun getSelectedPosition(): Int = selectedPosition

    fun setSelectedPosition(position: Int) {
        if (position in channels.indices && position != selectedPosition) {
            val previousItem = selectedPosition
            selectedPosition = position
            if (previousItem in channels.indices) {
                notifyItemChanged(previousItem)
            }
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int = channels.size
}