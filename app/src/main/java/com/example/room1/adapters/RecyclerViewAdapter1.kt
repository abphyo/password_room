package com.example.room1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.room1.R
import com.example.room1.room.Data

class RecyclerAdapter(data: List<Data?>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var data: List<Data?> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPack = data[position]
        holder.text.text = dataPack?.name
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.icon)
        val text: TextView = view.findViewById(R.id.recycleTextView)
        val browse: ImageView = view.findViewById(R.id.browse)
        val delete: ImageView = view.findViewById(R.id.delete)
    }
}