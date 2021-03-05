package com.vkochenkov.filmscatalog.view.recycler.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film

class FilmsAdapter(private val clickListener: FilmItemClickListener) :
    RecyclerView.Adapter<FilmViewHolder>() {

    private var itemsList: List<Film> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val filmItem = itemsList[position]
        holder.bind(filmItem)
        setOnClickListenerForDetailsBtn(holder, filmItem)
        setOnClickListenerForLikeBtn(holder, filmItem, position)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun refreshDataList(itemsList: List<Film>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }

    private fun setOnClickListenerForDetailsBtn(holder: FilmViewHolder, filmItem: Film) {
        holder.filmDetailsBtn.setOnClickListener {
            clickListener.detailsClickListener(filmItem)
        }
    }

    private fun setOnClickListenerForLikeBtn(
        holder: FilmViewHolder,
        filmItem: Film,
        position: Int
    ) {
        holder.filmLikeBtn.setOnClickListener {
            clickListener.likeClickListener(filmItem, position)
        }
    }
}