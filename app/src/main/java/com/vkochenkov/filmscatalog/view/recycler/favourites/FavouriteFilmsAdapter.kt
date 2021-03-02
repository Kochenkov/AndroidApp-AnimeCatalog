package com.vkochenkov.filmscatalog.view.recycler.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film

class FavouriteFilmsAdapter(private val clickListener: FavouriteFilmItemClickListener) :
    RecyclerView.Adapter<FavouriteFilmViewHolder>() {

    private var itemsList: List<Film> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteFilmViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_film, parent, false)
        return FavouriteFilmViewHolder(
            view
        )
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

    fun setData(itemsList: List<Film>) {
        this.itemsList = itemsList
    }

    fun refreshDataList(itemsList: List<Film>) {
        setData(itemsList)
        notifyDataSetChanged()
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