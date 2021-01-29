package com.vkochenkov.filmscatalog.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.model.Film

class FilmsAdapter(private val itemsList: List<Film>, val clickListener: (film: Film) -> Unit) :
    RecyclerView.Adapter<FilmViewHolder>() {

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
            DataStorage.previousSelectedFilm = DataStorage.currentSelectedFilm
            DataStorage.previousSelectedFilm?.selected = false
            filmItem.selected = true
            DataStorage.currentSelectedFilm = filmItem

            notifyDataSetChanged()

            clickListener(filmItem)
        }
    }

    private fun setOnClickListenerForLikeBtn(
        holder: FilmViewHolder,
        filmItem: Film,
        position: Int
    ) {
        holder.filmLikeBtn.setOnClickListener {
            if (filmItem.liked) {
                filmItem.liked = false
                DataStorage.favouriteFilmsList.remove(filmItem)
            } else {
                filmItem.liked = true
                DataStorage.favouriteFilmsList.add(filmItem)
            }
            notifyItemChanged(position)
        }
    }
}