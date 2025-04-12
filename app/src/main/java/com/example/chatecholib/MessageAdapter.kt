package com.example.chatecholib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private val messages = mutableListOf<Message>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        val item = messages[position]
        holder.tvMessageText.text = item.text
        holder.tvMessageTime.text = item.time
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if(item.isInComing) {
            params.marginStart = 50
            params.marginEnd = 0
        } else {
            params.marginStart = 0
            params.marginEnd = 50
        }
        holder.itemView.layoutParams = params
    }

    override fun getItemCount(): Int {
        return messages.size
    }
    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessageText : TextView = view.findViewById(R.id.tvMessageText)
        val tvMessageTime : TextView = view.findViewById(R.id.tvMessageTime)
    }
}