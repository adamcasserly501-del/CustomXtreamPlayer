package com.custom.xtreamplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ChannelAdapter(
    private val channels: List<ChannelEntity>,
    private val onClick: (ChannelEntity) -> Unit,
    private val onLongClick: ((ChannelEntity) -> Unit)? = null
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    private var selectedStreamId: String? = null

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.ivChannelLogo)
        val name: TextView = view.findViewById(R.id.tvChannelName)
        val favourite: TextView = view.findViewById(R.id.tvChannelFavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_live_channel, parent, false)

        view.isFocusable = true
        view.isClickable = true
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channels[position]

        holder.name.text = channel.name
        holder.favourite.text = if (channel.isFavorite) "★" else ""

        holder.logo.setImageResource(android.R.drawable.ic_menu_gallery)
        if (channel.iconUrl.isNotBlank()) {
            holder.logo.load(channel.iconUrl) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_gallery)
            }
        }

        holder.itemView.isSelected = selectedStreamId == channel.streamId
        holder.itemView.setOnClickListener {
            val p = holder.bindingAdapterPosition
            if (p != RecyclerView.NO_POSITION) {
                setSelectedChannel(channel.streamId)
                onClick(channels[p])
            }
        }

        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(channel)
            true
        }
    }

    fun setSelectedChannel(streamId: String?) {
        if (streamId == selectedStreamId) return

        val oldId = selectedStreamId
        selectedStreamId = streamId

        oldId?.let { id ->
            val oldIndex = channels.indexOfFirst { it.streamId == id }
            if (oldIndex >= 0) notifyItemChanged(oldIndex)
        }

        streamId?.let { id ->
            val newIndex = channels.indexOfFirst { it.streamId == id }
            if (newIndex >= 0) {
                notifyItemChanged(newIndex)
            }
        }
    }

    fun getSelectedStreamId(): String? = selectedStreamId

    override fun getItemCount(): Int = channels.size
}
