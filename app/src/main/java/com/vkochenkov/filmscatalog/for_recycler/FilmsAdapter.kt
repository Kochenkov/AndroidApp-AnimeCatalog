package com.vkochenkov.filmscatalog.for_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Film

class FilmsAdapter(private val itemsList: Array<Film>, val clickListener: (film: Film) -> Unit): RecyclerView.Adapter<FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val filmItem = itemsList[position]
        holder.bind(filmItem)
        holder.filmDetailsBtn.setOnClickListener {
            clickListener(filmItem)
        }
    }

    override fun getItemCount(): Int {
       return itemsList.size
    }
}