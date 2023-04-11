package com.example.realtime_database_activity

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class MessagesAdapter(
    private val context: Context,
    private val messages: List<Message>,
    private val currentUserUid: String
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.message_text)
        val timeText: TextView = itemView.findViewById(R.id.time_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.text

        val instant = Instant.ofEpochMilli(message.timestamp.toLong())
        val zoneId = ZoneId.systemDefault()
        val dateTime = LocalDateTime.ofInstant(instant, zoneId)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = dateTime.format(formatter)

        holder.timeText.text = time.toString()

        val gravity = if (message.senderId == currentUserUid)
            Gravity.END  else Gravity.START




        setLayoutParams(holder.messageText, gravity)
        setLayoutParams(holder.timeText, gravity)


    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setLayoutParams(view: View, gravity: Int) {
        val layoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.gravity = gravity
        view.layoutParams = layoutParams
    }
}
