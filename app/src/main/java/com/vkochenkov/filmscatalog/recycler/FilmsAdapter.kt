package com.vkochenkov.filmscatalog.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Film

class FilmsAdapter(private val itemsList: Array<Film>, val clickListener: (film: Film) -> Unit) :
    RecyclerView.Adapter<FilmViewHolder>() {

    var currentSelectedFilm: Film? = DataStorage.currentSelectedFilm
    var previousSelectedFilm: Film? = DataStorage.previousSelectedFilm

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

    private fun setOnClickListenerForDetailsBtn(holder: FilmViewHolder, filmItem: Film) {
        holder.filmDetailsBtn.setOnClickListener {
            previousSelectedFilm = currentSelectedFilm
            previousSelectedFilm?.selected = false

            filmItem.selected = true
            currentSelectedFilm = filmItem

            DataStorage.currentSelectedFilm = currentSelectedFilm
            DataStorage.previousSelectedFilm = previousSelectedFilm

            notifyDataSetChanged()

            clickListener(filmItem)
        }
    }

    private fun setOnClickListenerForLikeBtn(holder: FilmViewHolder, filmItem: Film, position: Int) {
        holder.filmLikeBtn.setOnClickListener {
            filmItem.liked = !filmItem.liked
            notifyItemChanged(position)
        }
    }
}