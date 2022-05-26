package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CharacterAdapter (val data: MutableList<String>, val id: MutableList<String>): RecyclerView.Adapter<CharacterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(layoutInflater.inflate(R.layout.item_character, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = data[position]
        val itemId = id[position]
        holder.bind(item, itemId)
    }

    override fun getItemCount(): Int = data.size

}