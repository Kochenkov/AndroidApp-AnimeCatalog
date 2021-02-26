package com.vkochenkov.filmscatalog.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.Film

class FavouriteFilmsAdapter(
    private val itemsList: List<Film>,
    private val clickListener: FavouriteFilmItemClickListener
) :
    RecyclerView.Adapter<FavouriteFilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteFilmViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_film, parent, false)
        return FavouriteFilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteFilmViewHolder, position: Int) {
        val filmItem = itemsList[position]
        holder.bind(filmItem)
        setOnClickListenerForDetailsBtn(holder, filmItem)
        setOnClickListenerForDeleteBtn(holder, filmItem, position)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    private fun setOnClickListenerForDetailsBtn(holder: FavouriteFilmViewHolder, filmItem: Film) {
        holder.filmDetailsBtn.setOnClickListener {
            clickListener.detailsClickListener(filmItem)
        }
    }

    private fun setOnClickListenerForDeleteBtn(
        holder: FavouriteFilmViewHolder,
        filmItem: Film,
        position: Int
    ) {
        holder.filmDeleteBtn.setOnClickListener {
            clickListener.deleteClickListener(filmItem, position)
        }
    }
}