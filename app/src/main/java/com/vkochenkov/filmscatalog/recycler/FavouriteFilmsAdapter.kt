package com.vkochenkov.filmscatalog.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.model.Film

class FavouriteFilmsAdapter(
    private val itemsList: List<Film>, private val emptyListTextView: TextView,
    private val clickListener: (film: Film) -> Unit
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
            DataStorage.previousSelectedFilm = DataStorage.currentSelectedFilm
            DataStorage.previousSelectedFilm?.selected = false
            filmItem.selected = true
            DataStorage.currentSelectedFilm = filmItem

            notifyDataSetChanged()

            clickListener(filmItem)
        }
    }

    private fun setOnClickListenerForDeleteBtn(
        holder: FavouriteFilmViewHolder,
        filmItem: Film,
        position: Int
    ) {
        holder.filmDeleteBtn.setOnClickListener {
            filmItem.liked = false
            DataStorage.favouriteFilmsList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemChanged(position)
            showStubIfListEmpty()
        }
    }

    private fun showStubIfListEmpty() {
        if (itemsList.isEmpty()) {
            emptyListTextView.visibility = View.VISIBLE
        } else {
            emptyListTextView.visibility = View.GONE
        }
    }
}